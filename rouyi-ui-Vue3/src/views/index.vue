<template>
  <div class="card" style="margin-top: 20px;"></div>
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

    <div>
      <div class="card" style="margin-bottom: 20px;"></div>
    </div>

    <!-- Separate Visa Reminder Card -->
    <el-card class="visa-reminder" v-loading="visaLoading">
      <template #header>
        <div class="card-header">
          <span>VISA EXPIRY REMINDER</span>
          <div class="button-container">
            <el-button :icon="Refresh" circle @click="getVisaInfo" />
          </div>
        </div>
      </template>

      <div>
        <el-form :model="visaForm" label-width="150px">
          <el-form-item label="Visa Issued Date">
            <el-date-picker
                v-model="visaForm.issuedDate"
                type="date"
                placeholder="Select Visa Issued Date"
                format="YYYY-MM-DD"
            />
          </el-form-item>

          <!-- Buttons Container -->
          <el-form-item>
            <el-button type="primary" size="medium" @click="saveVisaInfo">Save</el-button>
            <el-button type="danger" size="medium" @click="clearVisaInfo">Clear</el-button>
          </el-form-item>
        </el-form>
      </div>

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

  <div class="card" style="margin-top: 20px;"></div>

  <div class="welcome-container">
    <!-- 欢迎标题 -->
    <h1 class="welcome-title">Welcome To Use EI-PUTRA Visa Management System</h1>

    <!-- 描述内容 -->
    <p>
      This system was originally designed to solve the problem of the international student visa processing being too cumbersome,
      and at the same time, it provides a more flexible application method than Google forms. However, as the development progresses,
      the population served by this system has also been expanded to all people who need to apply for visas through I-PUTRA:
      including faculty, students or relatives of employees, etc.
    </p>
    <p>
      At the same time, this system also provides I-PUTRA managers with a simple graphical interface to view the application status of all applicants,
      as well as pages such as appointment data statistics and application data statistics.
    </p>
    <p>
      This system also adds appointment functions and fast offline document submission functions. The appointment function is designed to help I-PUTRA
      departments better arrange opening hours or the number of open counters to improve office efficiency. The fast offline document submission function
      is designed to achieve simple operations such as submitting original passports without queuing, reducing students' waiting time.
    </p>

    <!-- 致谢部分 -->
    <h2>Acknowledgements:</h2>
    <p>
      First of all, I would like to thank my supervisor Dr. Noris binti Mohd Norowi for his guidance and companionship along the way.
      Secondly, I would like to thank all I-PUTRA employees and students and employees who participated in the questionnaire survey for their strong support.
      Without your support, there would be no EI-PUTRA Visa Management System today. Finally, I would like to thank the author RuoYi and Tech Shrimp for their
      contributions to this project through open source code.
    </p>

    <!-- 作者信息 -->
    <p class="author-info">
      Project author: Chang Zeyu<br />
      January 22, 2025
    </p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue';
import { ElMessage } from 'element-plus';
import { Refresh, User, CopyDocument, InfoFilled, Calendar } from '@element-plus/icons-vue';
import { getMySelfReviewer } from '@/api/system/application.js';

import defAva from '@/assets/images/profile.jpg';
import useUserStore from "@/store/modules/user.js";

const baseURL = import.meta.env.VITE_APP_BASE_API;
const reviewer = ref(null);
const loading = ref(false);
const visaLoading = ref(false);

const visaForm = ref({
  issuedDate: null,
});
const visaDaysRemaining = ref(null);
const userStore = useUserStore();
const avatarUrl = computed(() => {
  if (!reviewer.value?.avatar) {
    return defAva;
  }
  return baseURL + reviewer.value.avatar;
});

const getVisaInfo = () => {
  visaLoading.value = true;
  setTimeout(() => {
    // 模拟获取签证数据（可替换为实际的 API 请求逻辑）
    if (visaForm.value.issuedDate) {
      const startTime = new Date(visaForm.value.issuedDate);
      const visaExpiryTime = new Date(startTime.setMonth(startTime.getMonth() + 10));
      const now = new Date();
      visaDaysRemaining.value = Math.floor((visaExpiryTime - now) / (1000 * 60 * 60 * 24));
    } else {
      visaDaysRemaining.value = null;
    }
    visaLoading.value = false;
    ElMessage.success('Visa information refreshed successfully');
  }, 1000); // 模拟加载延迟
};

const saveVisaInfo = () => {
  if (!visaForm.value.issuedDate) {
    ElMessage.warning('Please select a visa issued date.');
    return;
  }

  // 计算签证到期日期
  const issuedDate = new Date(visaForm.value.issuedDate);
  const expiryDate = new Date(issuedDate.setMonth(issuedDate.getMonth() + 10)).toISOString().split('T')[0];

  // 将签证到期日期保存到 localStorage
  localStorage.setItem('visaExpiryDate', expiryDate);

  // 更新剩余天数
  visaDaysRemaining.value = Math.floor((new Date(expiryDate) - new Date()) / (1000 * 60 * 60 * 24));

  ElMessage.success('Visa expiry date saved successfully.');
};

const clearVisaInfo = () => {
  visaForm.value.issuedDate = null; // 清除表单中的签证日期
  visaDaysRemaining.value = null;  // 重置剩余天数
  localStorage.removeItem('visaExpiryDate'); // 从 localStorage 中移除签证到期日期
  ElMessage.success('Visa information cleared successfully.');
};

// Visa-related computed properties
const visaReminderText = computed(() => {
  if (visaDaysRemaining.value === null || visaDaysRemaining.value < 0) {
    return 'Your visa already expired, please go to immigration office to apply for a special pass.';
  }
  if (visaDaysRemaining.value <= 30) {
    return 'Closing period - Immediate action required.';
  }
  if (visaDaysRemaining.value <= 60) {
    return 'Please remember to renew your visa.';
  }
  return 'Visa Status Normal.';
});

const visaAlertType = computed(() => {
  if (visaDaysRemaining.value === null || visaDaysRemaining.value < 0) {
    return 'error';
  }
  if (visaDaysRemaining.value <= 30) {
    return 'warning';
  }
  if (visaDaysRemaining.value <= 60) {
    return 'info';
  }
  return 'success';
});

const visaTagType = computed(() => {
  if (visaDaysRemaining.value === null || visaDaysRemaining.value < 0) {
    return 'danger';
  }
  if (visaDaysRemaining.value <= 30) {
    return 'warning';
  }
  if (visaDaysRemaining.value <= 60) {
    return 'warning';
  }
  return 'success';
});

// Load visa information from localStorage
const loadVisaInfo = () => {
  const expiryDate = localStorage.getItem('visaExpiryDate');
  if (expiryDate) {
    // 计算并更新剩余天数
    visaDaysRemaining.value = Math.floor((new Date(expiryDate) - new Date()) / (1000 * 60 * 60 * 24));
  } else {
    visaDaysRemaining.value = null; // 如果没有数据，显示为空
  }
};


// Save visa issued date
const saveVisaIssuedDate = () => {
  if (!visaForm.value.issuedDate) {
    ElMessage.warning('Please select a visa issued date.');
    return;
  }

  const issuedDate = new Date(visaForm.value.issuedDate);
  const expiryDate = new Date(issuedDate.setMonth(issuedDate.getMonth() + 10)).toISOString().split('T')[0];
  visaDaysRemaining.value = Math.floor((new Date(expiryDate) - new Date()) / (1000 * 60 * 60 * 24));

  // Save expiry date to localStorage
  localStorage.setItem('visaExpiryDate', expiryDate);
  ElMessage.success('Visa issued date saved successfully.');
};

const getReviewerInfo = async () => {
  loading.value = true;
  try {
    const res = await getMySelfReviewer();
    if (res.code === 200) {
      reviewer.value = res.data;
    } else {
      ElMessage.error(res.msg || 'Failed to fetch reviewer info.');
    }
  } catch (error) {
    ElMessage.error('Failed to fetch reviewer info.');
  } finally {
    loading.value = false;
  }
};

const handleAvatarError = () => {
  ElMessage.warning('Failed to load avatar.');
};

// Initial loading
onMounted(() => {
  getReviewerInfo();
  loadVisaInfo();
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
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button-container {
  display: flex;
  gap: 10px;
}

.welcome-container {
  padding: 20px;
  background-color: #f9f9f9; /* 背景颜色 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 阴影 */
}

.welcome-title {
  font-size: 24px; /* 字体大小 */
  font-weight: bold; /* 加粗 */
  color: #333; /* 字体颜色 */
  margin-bottom: 20px;
  text-align: center; /* 居中对齐 */
}

p {
  font-size: 16px;
  line-height: 1.6;
  color: #555;
  margin-bottom: 15px;
}

.author-info {
  font-style: italic;
  text-align: right; /* 右对齐 */
  color: #777;
}
</style>
