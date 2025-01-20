<template>
  <div class="reviewer-container">
    <!-- Staff Information Card -->
    <el-card class="reviewer-info" v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>REVIEWER INFORMATION</span>
          <div class="button-container">
            <el-button :icon="Refresh" circle @click="getReviewerInfo" />
          </div>
        </div>
      </template>

      <div v-if="reviewer?.userId" class="reviewer-content">
        <div class="avatar-container" style="display: flex; justify-content: center; align-items: center;">
          <el-avatar
              :size="100"
              :src="avatarUrl"
              @error="handleAvatarError"
          >
            <el-icon><User /></el-icon>
          </el-avatar>
        </div>

        <el-descriptions :column="2" border class="mt-20" style="margin-top: 20px;">
          <el-descriptions-item label="Staff Name">
            <el-tag size="small" type="success">
              {{ reviewer.nickName || '-' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="Staff ID">
            <el-tag size="small" type="info">
              {{ reviewer.userId || '-' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="Phone Number">
            <el-link
                type="primary"
                :underline="false"
                v-if="reviewer.phonenumber"
                @click="copyToClipboard(reviewer.phonenumber)"
            >
              {{ reviewer.phonenumber }}
              <el-icon class="ml-5"><CopyDocument /></el-icon>
            </el-link>
            <span v-else>-</span>
          </el-descriptions-item>
          <el-descriptions-item label="Staff Email">
            <el-link
                type="primary"
                :underline="false"
                v-if="reviewer.email"
                @click="copyToClipboard(reviewer.email)"
            >
              {{ reviewer.email }}
              <el-icon class="ml-5"><CopyDocument /></el-icon>
            </el-link>
            <span v-else>-</span>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <el-empty v-else description="No Reviewer Assigned" :image-size="100">
        <template #image>
          <el-icon :size="40" class="empty-icon">
            <User />
          </el-icon>
        </template>
      </el-empty>
    </el-card>

    <!-- Separate Visa Reminder Card -->
    <el-card class="visa-reminder" v-loading="visaLoading">
      <template #header>
        <div class="card-header">
          <span>VISA EXPIRY REMINDER</span>
          <div class="button-container">
            <el-tooltip content="Visa validity period is 10 months" placement="top">
              <el-icon class="info-icon"><InfoFilled /></el-icon>
            </el-tooltip>
            <el-button :icon="Refresh" circle @click="getVisaInfo" />
          </div>
        </div>
      </template>

      <div v-if="visaDaysRemaining !== null" class="visa-content">
        <el-alert
            :type="visaAlertType"
            :closable="false"
            show-icon
        >
          <template #title>
            <div class="visa-alert-content">
              <span class="visa-status">{{ visaReminderText }}</span>
              <el-tag :type="visaTagType" class="ml-10 visa-days-tag">
                {{ visaDaysRemaining >= 0 ? `${visaDaysRemaining} Days Remaining` : 'No Visa Application' }}
              </el-tag>
            </div>
          </template>
        </el-alert>
      </div>

      <el-empty v-else description="No Visa Information Available" :image-size="100">
        <template #image>
          <el-icon :size="40" class="empty-icon">
            <Calendar />
          </el-icon>
        </template>
      </el-empty>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { ElMessage } from 'element-plus';
import { Refresh, User, CopyDocument, InfoFilled, Calendar } from '@element-plus/icons-vue';
import { getMySelfReviewer } from '@/api/system/application.js';
import { getFile_submit } from '@/api/file_submit/file_submit.js';

import defAva from '@/assets/images/profile.jpg';
import useUserStore from "@/store/modules/user.js";

const baseURL = import.meta.env.VITE_APP_BASE_API;
const reviewer = ref(null);
const loading = ref(false);
const visaDaysRemaining = ref(null);
const userStore = useUserStore();
const userId = userStore.id;

const avatarUrl = computed(() => {
  if (!reviewer.value?.avatar) {
    return defAva;
  }
  return baseURL + reviewer.value.avatar;
});

const visaReminderText = computed(() => {
  if (visaDaysRemaining.value === null || visaDaysRemaining.value < 0) {
    return 'No Active Visa Application'
  }
  if (visaDaysRemaining.value <= 30) {
    return 'Visa Expiring Soon - Immediate Action Required'
  }
  return 'Visa Status Normal'
});

const handleAvatarError = () => {
  ElMessage.warning('Failed to load avatar.');
};

const copyToClipboard = async (text) => {
  try {
    await navigator.clipboard.writeText(text);
    ElMessage.success('Copied successfully');
  } catch (err) {
    ElMessage.error('Copy failed');
  }
};

const getVisaExpiryInfo = async () => {
  try {
    const res = await getFile_submit(userId);
    if (res.code === 200 && res.data?.updateTime) {
      const startTime = new Date(res.data.updateTime);
      const visaExpiryTime = new Date(startTime.setMonth(startTime.getMonth() + 10));
      const now = new Date();
      visaDaysRemaining.value = Math.floor((visaExpiryTime - now) / (1000 * 60 * 60 * 24));
    } else {
      visaDaysRemaining.value = null;
    }
  } catch (error) {
    console.error('Failed to fetch visa expiry info:', error);
    visaDaysRemaining.value = null;
  }
};

const getReviewerInfo = async () => {
  loading.value = true;
  try {
    const res = await getMySelfReviewer();
    if (res.code === 200) {
      reviewer.value = res.data;
      await getVisaExpiryInfo();
    } else {
      ElMessage.error(res.msg || 'Failed to fetch reviewer info');
    }
  } catch (error) {
    ElMessage.error('Failed to fetch reviewer info');
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  getReviewerInfo();
});
</script>

<style scoped>
.visa-alert-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 16px;
  padding: 10px 0;
  gap: 20px;
}

.visa-status {
  font-size: 16px;
  font-weight: 500;
}

.visa-days-tag {
  font-size: 18px !important;
  padding: 8px 15px !important;
  height: auto !important;
  line-height: 1.5 !important;
  margin-left: 20px; /* 增加额外的左侧间距 */
}

:deep(.el-alert__title) {
  font-size: 16px !important;
  line-height: 1.5 !important;
}

:deep(.el-alert) {
  padding: 15px 20px;
}

:deep(.el-tag) {
  font-weight: 500;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
