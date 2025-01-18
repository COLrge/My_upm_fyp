<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="ID of the related activity" prop="activityId">
        <el-input
          v-model="queryParams.activityId"
          placeholder="请输入ID of the related activity"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Appointment name" prop="appointmentName">
        <el-input
          v-model="queryParams.appointmentName"
          placeholder="请输入Appointment name"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['appointment_record:appointment_record:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['appointment_record:appointment_record:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['appointment_record:appointment_record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['appointment_record:appointment_record:export']"
        >导出</el-button>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['appointment_record:appointment_record:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['appointment_record:appointment_record:remove']">删除</el-button>
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

    <!-- 添加或修改Appointment list对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="appointment_recordRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Appointment time" prop="appointmentTime">
          <el-select v-model="form.appointmentTime" placeholder="请选择Appointment time">
            <el-option
              v-for="dict in visa_appointment_time"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Remarks" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入Remarks" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Appointment_record">
import { listAppointment_record, getAppointment_record, delAppointment_record, addAppointment_record, updateAppointment_record } from "@/api/appointment_record/appointment_record";
import useUserStore from "@/store/modules/user.js";

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
      { required: true, message: "ID of the related activity不能为空", trigger: "blur" }
    ],
    appointmentName: [
      { required: true, message: "Appointment name不能为空", trigger: "blur" }
    ],
    userId: [
      { required: true, message: "User ID不能为空", trigger: "blur" }
    ],
    userName: [
      { required: true, message: "User name不能为空", trigger: "blur" }
    ],
    appointmentTime: [
      { required: true, message: "Appointment time不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询Appointment list列表 */
const userStore = useUserStore();

// 确保将 ID 转换为 BigInt 进行比较
const ADMIN_IDS = [BigInt(1), BigInt(5)];

async function getList() {
  // 确保用户信息已加载
  if (!userStore.id || !userStore.name) {
    await userStore.getInfo();
  }

  // 确保 currentUserId 是 BigInt 类型
  const currentUserId = BigInt(userStore.id);

  try {
    loading.value = true;
    const response = await listAppointment_record(queryParams.value);

    // 检查是否是管理员用户
    if (ADMIN_IDS.includes(currentUserId)) {
      // 管理员可以查看所有记录
      appointment_recordList.value = response.rows;
      total.value = response.total;
    } else {
      // 普通用户只能查看自己的记录
      const userAppointments = response.rows.filter(item => BigInt(item.userId) === currentUserId);
      appointment_recordList.value = userAppointments;
      total.value = userAppointments.length;
    }
  } catch (error) {
    console.error('获取预约记录失败:', error);
    // 可以添加错误提示
  } finally {
    loading.value = false;
  }
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
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

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加Appointment list";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAppointment_record(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改Appointment list";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["appointment_recordRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAppointment_record(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAppointment_record(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除Appointment list编号为"' + _ids + '"的数据项？').then(function() {
    return delAppointment_record(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('appointment_record/appointment_record/export', {
    ...queryParams.value
  }, `appointment_record_${new Date().getTime()}.xlsx`)
}

getList();
</script>
