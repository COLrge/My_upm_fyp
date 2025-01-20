<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="File Name" prop="fileName">
        <el-input
            v-model="queryParams.fileName"
            placeholder="Please enter file name"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="User ID" prop="userId">
        <el-input
            v-model="queryParams.userId"
            placeholder="Please enter user ID"
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
            v-hasPermi="['file_submit:file_submit:add']"
        >Add</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['file_submit:file_submit:edit']"
        >Edit</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['file_submit:file_submit:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['file_submit:file_submit:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="file_submitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="PIN" align="center" :formatter="idpin" />
      <el-table-column label="File Name" align="center" prop="fileName" />
      <el-table-column label="Submission Status" align="center" prop="submitStatus">
        <template #default="scope">
          <dict-tag :options="file_submit_state" :value="scope.row.submitStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="User ID" align="center" prop="userId" />
      <el-table-column label="User Name" align="center" prop="userName" />
      <el-table-column label="Remarks" align="center" prop="remark" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['file_submit:file_submit:edit']">Edit</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['file_submit:file_submit:remove']">Delete</el-button>
          <el-button link type="success" icon="Share" @click="handleGenerateQR(scope.row)">Get QR</el-button>
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

    <!-- Add or Edit File Submission Status Table Dialog -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="file_submitRef" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="File Name" prop="fileName">
          <el-input v-model="form.fileName" placeholder="Please enter file name" />
        </el-form-item>
        <el-form-item v-if="title !== 'Add File Submission Status Table'" label="Submission Status" prop="submitStatus">
          <el-select v-model="form.submitStatus" placeholder="Please select submission status">
            <el-option
                v-for="dict in file_submit_state"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Remarks" prop="remark">
          <el-input v-model="form.remark" placeholder="Please enter remarks" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">Confirm</el-button>
          <el-button @click="cancel">Cancel</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="File_submit">
import { listFile_submit, getFile_submit, delFile_submit, addFile_submit, updateFile_submit } from "@/api/file_submit/file_submit";
import {ElMessage, ElMessageBox} from "element-plus";
import {listApplication} from "@/api/system/application.js";
import useUserStore from "@/store/modules/user.js";

const { proxy } = getCurrentInstance();
const { file_submit_state } = proxy.useDict('file_submit_state');

const file_submitList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: null,
    userId: null,
  },
  rules: {
    fileName: [
      { required: true, message: "File Name cannot be empty", trigger: "blur" }
    ],
    submitStatus: [
      { required: true, message: "Submission Status cannot be empty", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

const idpin = (row, column, cellValue, index) => {
  return row.id + 2025;
};

/** Query File Submission Status Table List */
const userStore = useUserStore();

// Ensure ID is converted to BigInt for comparison
const ADMIN_IDS = [BigInt(1), BigInt(5)];

async function getList() {
  // Ensure user info is loaded
  if (!userStore.id || !userStore.name) {
    await userStore.getInfo();
  }

  // Ensure currentUserId is BigInt
  const currentUserId = BigInt(userStore.id);

  try {
    loading.value = true;
    const response = await listFile_submit(queryParams.value);

    // Check if the user is an admin
    if (ADMIN_IDS.includes(currentUserId)) {
      // Admin can view all records
      file_submitList.value = response.rows;
      total.value = response.total;
    } else {
      // Regular users can only view their own records
      const userSubmissions = response.rows.filter(item => BigInt(item.userId) === currentUserId);
      file_submitList.value = userSubmissions;
      total.value = userSubmissions.length;
    }
  } catch (error) {
    console.error('Failed to fetch submission records:', error);
  } finally {
    loading.value = false;
  }
}

// Cancel button
function cancel() {
  open.value = false;
  reset();
}

// Form reset
function reset() {
  form.value = {
    id: null,
    fileName: null,
    submitStatus: null,
    userId: null,
    userName: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("file_submitRef");
}

/** Generate QR */
function handleGenerateQR(row) {
  const idpinValue = idpin(row); // Call idpin method to get idpin value
  const qrUrl = `http://localhost/submission/QR_submit?pin=${idpinValue}`;
  window.location.href = qrUrl; // Redirect to QR generation page
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

// Handle selection change
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** Add button operation */
async function handleAdd() {
  const userStore = useUserStore();
  if (!userStore.id || !userStore.name) {
    await userStore.getInfo();
  }

  try {
    // Get application list
    const response = await listApplication();
    const applications = response.rows || [];

    // Find the current user's application record
    const application = applications.find(item =>
        String(item.applicantId) === String(userStore.id)
    );
    //console.log('Found application record:', application);

    // Validate status
    if (application?.status === 'APPROVED') {
      reset();

      // Auto-fill current user info
      form.value.userId = userStore.id; // Set current user ID
      form.value.userName = userStore.name; // Set current user name
      form.value.submitStatus = "1";

      open.value = true;
      title.value = "Add File Submission Status Table";
    } else {
      ElMessage.error("Your application status is not approved, cannot add new records");
    }
  } catch (error) {
    console.error('Failed to fetch application list:', error);
    ElMessage.error('Operation failed, please try again later');
  }
}

/** Edit button operation */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;

  ElMessageBox.prompt("Please enter PIN to verify edit permission", "Edit Verification", {
    inputType: "number",
    confirmButtonText: "Confirm",
    cancelButtonText: "Cancel",
  })
      .then(({ value }) => {
        if (parseInt(value) === _id) {
          getFile_submit(_id).then((response) => {
            form.value = response.data;
            open.value = true;
            title.value = "Edit File Submission Status Table";
            ElMessage.success("PIN verification successful");
          });
        } else {
          ElMessage.error("Incorrect PIN, cannot proceed with edit");
        }
      })
      .catch(() => {
        ElMessage.info("Edit operation canceled");
      });
}

/** Submit form */
function submitForm() {
  proxy.$refs["file_submitRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateFile_submit(form.value).then(response => {
          proxy.$modal.msgSuccess("Update successful");
          open.value = false;
          getList();
        });
      } else {
        addFile_submit(form.value).then(response => {
          proxy.$modal.msgSuccess("Add successful");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** Delete button operation */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('Are you sure you want to delete the file submission with ID "' + _ids + '"?').then(function() {
    return delFile_submit(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("Delete successful");
  }).catch(() => {});
}

/** Export button operation */
function handleExport() {
  proxy.download('file_submit/file_submit/export', {
    ...queryParams.value
  }, `file_submit_${new Date().getTime()}.xlsx`)
}

getList();
</script>