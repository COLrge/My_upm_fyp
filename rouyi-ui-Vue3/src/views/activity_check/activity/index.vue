<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="Activity's Name" prop="activityName">
        <el-input
            v-model="queryParams.activityName"
            placeholder="Please enter activity's name"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Open Time" prop="openTime">
        <el-date-picker clearable
                        v-model="queryParams.openTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="Please select Open time">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Close Time" prop="closeTime">
        <el-date-picker clearable
                        v-model="queryParams.closeTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="Please select Close time">
        </el-date-picker>
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
            v-hasPermi="['activity_check:activity:add']"
        >Add</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['activity_check:activity:edit']"
        >Edit</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['activity_check:activity:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['activity_check:activity:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary Key ID" align="center" prop="id" />
      <el-table-column label="Activity's Name" align="center" prop="activityName" />
      <el-table-column label="Content of the Activity" align="center" prop="activityContent" >
        <template #default="scope">
          <div class="no-tags" v-html="scope.row.activityContent"></div>
        </template>
      </el-table-column>
      <el-table-column label="Open Time" align="center" prop="openTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.openTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Close Time" align="center" prop="closeTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.closeTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Illustration Image" align="center" prop="activityImage" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.activityImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="Remarks" align="center" prop="remark" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['activity_check:activity:edit']">Edit</el-button>
          <el-button type="text" size="default" @click="book(scope.row)">Book</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['activity_check:activity:remove']">Delete</el-button>
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

    <!-- Add or Edit Activity List Dialog -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form ref="activityRef" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="Activity's Name" prop="activityName">
          <el-input v-model="form.activityName" placeholder="Please enter activity's name" />
        </el-form-item>
        <el-form-item label="Content of the Activity">
          <editor v-model="form.activityContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="Open Time" prop="openTime">
          <el-date-picker clearable
                          v-model="form.openTime"
                          type="datetime"
                          value-format="YYYY-MM-DD HH:mm"
                          placeholder="Please select Open time">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Illustration Image" prop="activityImage">
          <image-upload v-model="form.activityImage"/>
        </el-form-item>
        <el-form-item label="Remarks" prop="remark">
          <el-input v-model="form.remark" placeholder="Please enter remarks" />
        </el-form-item>
        <el-form-item label="Close Time" prop="closeTime">
          <el-date-picker clearable
                          v-model="form.closeTime"
                          type="datetime"
                          value-format="YYYY-MM-DD HH:mm"
                          placeholder="Please select Close time">
          </el-date-picker>
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

<script setup name="Activity">
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router'; // Import Vue Router's Composition API
import { listActivity, getActivity, delActivity, addActivity, updateActivity } from "@/api/activity_check/activity";
import { addAppointment_record } from "@/api/appointment_record/appointment_record.js";
import useUserStore from "@/store/modules/user.js";

const { proxy } = getCurrentInstance();
const router = useRouter();

const activityList = ref([]);
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
    activityName: null,
    activityContent: null,
    openTime: null,
    closeTime: null
  },
  rules: {
    activityName: [
      { required: true, message: "Activity's name cannot be empty", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** Query Activity List */
function getList() {
  loading.value = true;
  listActivity(queryParams.value).then(response => {
    activityList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
    activityName: null,
    activityContent: null,
    openTime: null,
    activityImage: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null,
    remark: null,
    closeTime: null
  };
  proxy.resetForm("activityRef");
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
  title.value = "Add Activity List";
}

/** Edit button operation */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getActivity(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "Edit Activity List";
  });
}

/** Add Appointment */
const book = async (row) => {
  try {
    // Confirm if the user wants to make an appointment
    const confirm = await proxy.$confirm(
        'Are you sure you want to make an appointment? ' +
        '<br>You can change the default appointment time later in the appointment list.',
        'Notice',
        {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning',
          dangerouslyUseHTMLString: true,
        }
    );

    if (confirm) {
      // Get user store instance
      const userStore = useUserStore();
      // Ensure user info is loaded
      if (!userStore.id || !userStore.name) {
        await userStore.getInfo(); // Fetch user info if not already fetched
      }

      // Get user info from Vuex Store
      const userId = userStore.id; // User ID
      const userName = userStore.name; // User Name
      const roles = userStore.roles

      // Construct new appointment record
      const newRecord = {
        activityId: row.id,
        appointmentName: row.activityName,
        userId: userId,
        userName: userName,
        roleId: "2",
        appointmentTime: '1',
      };

      // Call API to add appointment record
      await addAppointment_record(newRecord);
      proxy.$message.success('Record created successfully');
    }
  } catch (error) {
    if (error === 'cancel') {
      // User clicked cancel, no action needed
      proxy.$message.info('Appointment creation canceled');
    } else {
      proxy.$message.error('Failed to create appointment: ' + error.message);
    }
  }
};

/** Submit form */
function submitForm() {
  proxy.$refs["activityRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("Update successful");
          open.value = false;
          getList();
        });
      } else {
        addActivity(form.value).then(response => {
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
  proxy.$modal.confirm('Are you sure you want to delete the activity with ID "' + _ids + '"?').then(function() {
    return delActivity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("Delete successful");
  }).catch(() => {});
}

/** Export button operation */
function handleExport() {
  proxy.download('activity_check/activity/export', {
    ...queryParams.value
  }, `activity_${new Date().getTime()}.xlsx`)
}

getList();
</script>

<style scoped>
.no-tags p {
  display: none;
}
</style>