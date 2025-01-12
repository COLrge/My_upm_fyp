<template>
  <el-card class="reviewer-info" v-loading="loading">
    <template #header>
      <div class="card-header">
        <span>STAFF INFOMATION</span>
        <el-button :icon="Refresh" circle @click="getReviewerInfo" />
      </div>
    </template>

    <div v-if="reviewer?.userId" class="reviewer-content">
      <div class="avatar-container">
        <el-avatar
            :size="100"
            :src="avatarUrl"
            @error="handleAvatarError"
        >
          <el-icon><User /></el-icon>
        </el-avatar>
      </div>

      <el-descriptions :column="2" border class="mt-20">
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

    <el-empty v-else description="暂无分配审核员" :image-size="100">
      <template #image>
        <el-icon :size="40" class="empty-icon">
          <User />
        </el-icon>
      </template>
    </el-empty>
  </el-card>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue'
import {ElMessage} from 'element-plus'
import {Refresh, User, CopyDocument} from '@element-plus/icons-vue'
import {getMySelfReviewer} from '@/api/system/application.js'
import defAva from '@/assets/images/profile.jpg'

const baseURL = import.meta.env.VITE_APP_BASE_API
const reviewer = ref(null)
const loading = ref(false)

const avatarUrl = computed(() => {
  if (!reviewer.value?.avatar) {
    return defAva
  }
  return baseURL + reviewer.value.avatar
})

const handleAvatarError = () => {
  ElMessage.warning('头像加载失败')
}

const copyToClipboard = async (text) => {
  try {
    await navigator.clipboard.writeText(text)
    ElMessage.success('复制成功')
  } catch (err) {
    ElMessage.error('复制失败')
  }
}

const getReviewerInfo = async () => {
  loading.value = true
  try {
    const res = await getMySelfReviewer()
    if (res.code === 200) {
      reviewer.value = res.data
    } else {
      ElMessage.error(res.msg || '获取审核员信息失败')
    }
  } catch (error) {
    ElMessage.error('获取审核员信息失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getReviewerInfo()
})
</script>

<style scoped>
.reviewer-info {
  margin: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.avatar-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.reviewer-content {
  padding: 20px 0;
}

.mt-20 {
  margin-top: 20px;
}

.ml-5 {
  margin-left: 5px;
}

.empty-icon {
  color: var(--el-text-color-secondary);
}

:deep(.el-descriptions__cell) {
  padding: 12px !important;
}
</style>
