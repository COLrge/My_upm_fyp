<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="Passport number" prop="passportNumber">
        <el-input
            v-model="queryParams.passportNumber"
            placeholder="Please enter Passport Number"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">Search</el-button>
        <el-button icon="Refresh" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['system:application:add']"
        >Add</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['system:application:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="Applicant ID" align="center" prop="applicantId" />
      <el-table-column label="Name" align="center" prop="name" />
      <el-table-column label="Matric Number" align="center" prop="matricNumber" />
      <el-table-column label="Passport Number" align="center" prop="passportNumber" />
      <el-table-column label="Email" align="center" prop="email" />
      <el-table-column label="Nationality" align="center" prop="nationality" />
      <el-table-column label="Course Name" align="center" prop="courseName" />
<!--      <el-table-column label="审核备注" align="center" prop="Remarks" />-->
      <el-table-column label="Status" align="center" prop="status" width="150">
        <template #default="{ row }">
          <el-tag
              :type="getStatusType(row.status)"
              effect="plain"
          >
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="review Remarks" align="center" prop="remarks" />
      <el-table-column label="Created Time" align="center" prop="createdAt" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createdAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column  label="Operations" align="center" class-name="small-padding fixed-width" width="300">
        <template #default="scope">
          <el-button
              link
              type="primary"
              icon="View"
              @click="handleView(scope.row)"
              v-hasPermi="['system:application:query']"
          >View</el-button>
          <el-button
              link
              type="primary"
              icon="Edit"
              v-if="scope.row.status === 'REJECTED'"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:application:edit']"
          >Edit</el-button>
          <el-button
              link
              type="primary"
              icon="Delete"
              v-if="scope.row.status === 'REJECTED'"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:application:remove']"
          >Delete</el-button>
          <el-button
              link
              type="primary"
              icon="UserFilled"
              @click="handleAssign(scope.row)"
              v-if="scope.row.status === 'SUBMITTED'"
              v-hasPermi="['system:application:assign']"
          >Assign Reviewer</el-button>
          <el-button
              link
              type="primary"
              icon="Check"
              @click="handleReview(scope.row)"
              v-if="scope.row.status === 'UNDER_REVIEW'"
              v-hasPermi="['system:application:review']"
          >Review</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <ApplicationForm
        v-model:visible="open"
        :title="title"
        :applicationId="applicationId"
        :readonly="readonly"
        @refresh="getList"
    />

    <el-dialog
      v-model="assignDialogVisible"
      title="Assign Reviewer"
      width="500px"
    >
      <el-form :model="assignForm" label-width="120px">
        <el-form-item label="Reviewer">
          <el-select v-model="assignForm.reviewerId" placeholder="Select reviewer">
            <el-option
              v-for="user in reviewerList"
              :key="user.userId"
              :label="user.nickName"
              :value="user.userId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="assignDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitAssign">Confirm</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog
      v-model="reviewDialogVisible"
      title="Review Application"
      width="500px"
    >
      <el-form :model="reviewForm" label-width="120px">
        <el-form-item label="Status">
          <el-select v-model="reviewForm.status" placeholder="Select status">
            <el-option label="Approved" value="APPROVED" />
            <el-option label="Rejected" value="REJECTED" />
          </el-select>
        </el-form-item>
        <el-form-item label="Remarks">
          <el-input
            v-model="reviewForm.remarks"
            type="textarea"
            placeholder="Enter review remarks"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="reviewDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitReview">Confirm</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Application">
import {listApplication, delApplication} from "@/api/system/application";
import ApplicationForm from './ApplicationForm.vue';

import {ElMessage, ElMessageBox} from 'element-plus'
import {ref, reactive, toRefs} from 'vue'

const {proxy} = getCurrentInstance();

const applicationList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const applicationId = ref(null);
const readonly = ref(false);

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    insuranceCompany: null,
  },
});

const {queryParams} = toRefs(data);


const getStatusType = (status) => {
  const statusMap = {
    'SUBMITTED': 'info',     // 蓝色
    'UNDER_REVIEW': 'warning', // 黄色
    'APPROVED': 'success',   // 绿色
    'REJECTED': 'danger'     // 红色
  }
  return statusMap[status]
}



/** Query Visa Application Form list */
function getList() {
  loading.value = true;
  listApplication(queryParams.value).then(response => {
    applicationList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** Search button operation */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** Reset button operation */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// Multiple selection box select data
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length !== 1;
  multiple.value = !selection.length;
}

/** New button operation */
function handleAdd() {
  applicationId.value = null;
  open.value = true;
  title.value = "Add Visa Application Form";
  readonly.value = false;
}

/** Modify button operation */
function handleUpdate(row) {
  applicationId.value = row.id || ids.value[0];
  open.value = true;
  title.value = "Modify Visa Application Form";
  readonly.value = false;
}

/** View button operation */
function handleView(row) {
  applicationId.value = row.id;
  open.value = true;
  title.value = "View Visa Application Form";
  readonly.value = true;
}

/** Delete button operation */
function handleDelete(row) {
  const applicationIds = row.id || ids.value;
  ElMessageBox.confirm('Are you sure to delete the selected Visa Application Form data items?', 'Warning', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    type: 'warning'
  }).then(() => {
    return delApplication(applicationIds);
  }).then(() => {
    getList();
    ElMessage({
      type: 'success',
      message: 'Deleted successfully'
    });
  }).catch(() => {
  });
}

/** Export button operation */
function handleExport() {
  proxy.download('system/application/export', {
    ...queryParams.value
  }, `application_${new Date().getTime()}.xlsx`)
}

import { dropdownListUser } from '@/api/system/role';
import { assignReviewer, reviewApplication } from '@/api/system/application';

// 添加数据
const assignDialogVisible = ref(false);
const reviewDialogVisible = ref(false);
const reviewerList = ref([]);
const assignForm = reactive({
  id: null,
  reviewerId: null
});
const reviewForm = reactive({
  id: null,
  status: '',
  remarks: ''
});

// 获取审核人员列表
function getReviewerList() {
  dropdownListUser({ roleId: 3 }).then(response => {
    reviewerList.value = response.data;
  });
}

// 打开分配审核人员对话框
function handleAssign(row) {
  assignForm.id = row.id;
  assignForm.reviewerId = row.reviewerId;
  assignDialogVisible.value = true;
  getReviewerList();
}

// 提交分配审核人员
function submitAssign() {
  if(!assignForm.reviewerId) {
    ElMessage.warning("Please select a reviewer");
    return;
  }

  assignReviewer(assignForm.id, assignForm.reviewerId).then(() => {
    ElMessage.success("Assigned successfully");
    assignDialogVisible.value = false;
    getList();
  });
}

// 打开审核对话框
function handleReview(row) {
  reviewForm.id = row.id;
  reviewForm.status = '';
  reviewForm.remarks = '';
  reviewDialogVisible.value = true;
}

// 提交审核
function submitReview() {
  if(!reviewForm.status) {
    ElMessage.warning("Please select status");
    return;
  }

  reviewApplication(
    reviewForm.id,
    reviewForm.status,
    reviewForm.remarks
  ).then(() => {
    ElMessage.success("Reviewed successfully");
    reviewDialogVisible.value = false;
    getList();
  });
}

getList();
</script>
