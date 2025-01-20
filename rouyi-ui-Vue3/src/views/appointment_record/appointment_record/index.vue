<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="180px">
      <el-form-item label="ID of the related activity" prop="activityId">
        <el-input
            v-model="queryParams.activityId"
            placeholder="Please enter ID of the related activity"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Appointment name" prop="appointmentName">
        <el-input
            v-model="queryParams.appointmentName"
            placeholder="Please enter appointment name"
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
        <!-- Add button can be placed here if needed -->
      </el-col>
      <el-col :span="1.5">
        <!-- Edit button can be placed here if needed -->
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['appointment_record:appointment_record:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['appointment_record:appointment_record:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appointment_recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary key ID" align="center" prop="id" />
      <el-table-column label="ID of the related activity" align="center" prop="activityId" />
      <el-table-column label="Appointment name" align="center" prop="appointmentName" />
      <el-table-column label="User ID" align="center" prop="userId" />
      <el-table-column label="User name" align="center" prop="userName" />
      <el-table-column label="Appointment time" align="center" prop="appointmentTime">
        <template #default="scope">
          <dict-tag :options="visa_appointment_time" :value="scope.row.appointmentTime"/>
        </template>
      </el-table-column>
      <el-table-column label="Remarks" align="center" prop="remark" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['appointment_record:appointment_record:edit']">Edit</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['appointment_record:appointment_record:remove']">Delete</el-button>
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

    <!-- Add or Edit Appointment List Dialog -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="appointment_recordRef" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="Appointment time" prop="appointmentTime">
          <el-select v-model="form.appointmentTime" placeholder="Please select appointment time">
            <el-option
                v-for="dict in visa_appointment_time"
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

<script setup name="Appointment_record">
import { listAppointment_record, getAppointment_record, delAppointment_record, addAppointment_record, updateAppointment_record } from "@/api/appointment_record/appointment_record";
import useUserStore from "@/store/modules/user.js";
import {getActivity} from "@/api/activity_check/activity.js";
import {ElMessage} from "element-plus";

const { proxy } = getCurrentInstance();
const { visa_appointment_time } = proxy.useDict('visa_appointment_time');

const appointment_recordList = ref([]);
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
    activityId: null,
    appointmentName: null,
  },
  rules: {
    activityId: [
      { required: true, message: "ID of the related activity cannot be empty", trigger: "blur" }
    ],
    appointmentName: [
      { required: true, message: "Appointment name cannot be empty", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "User ID cannot be empty", trigger: "blur" }
    ],
    userName: [
      { required: true, message: "User name cannot be empty", trigger: "blur" }
    ],
    appointmentTime: [
      { required: true, message: "Appointment time cannot be empty", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** Query Appointment List */
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
    const response = await listAppointment_record(queryParams.value);

    // Check if the user is an admin
    if (ADMIN_IDS.includes(currentUserId)) {
      // Admin can view all records
      appointment_recordList.value = response.rows;
      total.value = response.total;
    } else {
      // Regular users can only view their own records
      const userAppointments = response.rows.filter(item => BigInt(item.userId) === currentUserId);
      appointment_recordList.value = userAppointments;
      total.value = userAppointments.length;
    }
  } catch (error) {
    console.error('Failed to fetch appointment records:', error);
    // Optionally add an error message
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
    activityId: null,
    appointmentName: null,
    userId: null,
    userName: null,
    appointmentTime: null,
    roleId: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null
  };
  proxy.resetForm("appointment_recordRef");
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
function handleAdd() {
  reset();
  open.value = true;
  title.value = "Add Appointment List";
}

/** Edit button operation */
async function handleUpdate(row) {
  try {
    // Get the open_time of the activity
    const activityResponse = await getActivity(row.activityId);
    const openTime = new Date(activityResponse.data.openTime);

    if (new Date() >= openTime) {
      ElMessage.error('The activity open time has passed, cannot edit');
      return;
    }

    // Time validation passed, proceed with edit
    const _id = row.id;
    const response = await getAppointment_record(_id);
    reset();
    form.value = response.data;
    open.value = true;
    title.value = "Edit Appointment List";
  } catch (error) {
    ElMessage.error('Operation failed');
  }
}

/** Submit form */
function submitForm() {
  proxy.$refs["appointment_recordRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAppointment_record(form.value).then(response => {
          proxy.$modal.msgSuccess("Update successful");
          open.value = false;
          getList();
        });
      } else {
        addAppointment_record(form.value).then(response => {
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
  proxy.$modal.confirm('Are you sure you want to delete the appointment with ID "' + _ids + '"?').then(function() {
    return delAppointment_record(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("Delete successful");
  }).catch(() => {});
}

/** Export button operation */
function handleExport() {
  proxy.download('appointment_record/appointment_record/export', {
    ...queryParams.value
  }, `appointment_record_${new Date().getTime()}.xlsx`)
}

getList();
</script>