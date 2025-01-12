<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="activity's name" prop="activityName">
        <el-input
          v-model="queryParams.activityName"
          placeholder="请输入activity's name"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Open time" prop="openTime">
        <el-date-picker clearable
          v-model="queryParams.openTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择Open time">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Close time" prop="closeTime">
        <el-date-picker clearable
          v-model="queryParams.closeTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择Close time">
        </el-date-picker>
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
          v-hasPermi="['activity_check:activity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['activity_check:activity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['activity_check:activity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['activity_check:activity:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary key ID" align="center" prop="id" />
      <el-table-column label="activity's name" align="center" prop="activityName" />
      <el-table-column label="Content of the activity" align="center" prop="activityContent" />
      <el-table-column label="Open time" align="center" prop="openTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.openTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Close time" align="center" prop="closeTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.closeTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Illustration image" align="center" prop="activityImage" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.activityImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="Remarks" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['activity_check:activity:edit']">修改</el-button>
          <el-button type="text" size="default" @click="book(scope.row)" >预约</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['activity_check:activity:remove']">删除</el-button>
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

    <!-- 添加或修改Activity list对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="activityRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="activity's name" prop="activityName">
          <el-input v-model="form.activityName" placeholder="请输入activity's name" />
        </el-form-item>
        <el-form-item label="Content of the activity">
          <editor v-model="form.activityContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="Open time" prop="openTime">
          <el-date-picker clearable
            v-model="form.openTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm"
            placeholder="请选择Open time">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Illustration image" prop="activityImage">
          <image-upload v-model="form.activityImage"/>
        </el-form-item>
        <el-form-item label="Remarks" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入Remarks" />
        </el-form-item>
        <el-form-item label="Close time" prop="closeTime">
          <el-date-picker clearable
            v-model="form.closeTime"
            type="datetime"
            value-format="YYYY-MM-DD HH:mm"
            placeholder="请选择Close time">
          </el-date-picker>
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

<script setup name="Activity">
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router'; // 引入 Vue Router 的组合式 API
import { listActivity, getActivity, delActivity, addActivity, updateActivity } from "@/api/activity_check/activity";
import request from "@/utils/request.js";
import { addAppointment_record } from "@/api/appointment_record/appointment_record.js";
import { useStore } from 'vuex';
import useUserStore from "../../../store/modules/user.js"; // 引入 Vuex 的 useStore 钩子

const { proxy } = getCurrentInstance();
const router = useRouter();
const store = useStore(); // 获取 Vuex Store 实例

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
      { required: true, message: "activity's name不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询Activity list列表 */
function getList() {
  loading.value = true;
  listActivity(queryParams.value).then(response => {
    activityList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
  title.value = "添加Activity list";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getActivity(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改Activity list";
  });
}

/** 新增预约 */
const book = async (row) => {
  try {
    // 弹出消息盒子确认是否预约
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
      // 获取 user store 实例
      const userStore = useUserStore();
      // 确保用户信息已经加载
      if (!userStore.id || !userStore.name) {
        await userStore.getInfo(); // 获取用户信息（如果没有获取过）
      }

      // 从 Vuex Store 获取用户信息
      const userId = userStore.id; // 用户ID
      const userName = userStore.name; // 用户名

      // 构造新预约记录
      const newRecord = {
        activityId: row.id,
        appointmentName: row.activityName,
        userId: userId,
        userName: userName,
        appointmentTime: '1',
      };

      // 调用新增预约记录的接口
      await addAppointment_record(newRecord);
      proxy.$message.success('Record created successful');
    }
  } catch (error) {
    if (error === 'cancel') {
      // 用户点击了取消，无需操作
      proxy.$message.info('Cancel creating');
    } else {
      proxy.$message.error('creating fail');
    }
  }
};

/** 提交按钮 */
function submitForm() {
  proxy.$refs["activityRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addActivity(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除Activity list编号为"' + _ids + '"的数据项？').then(function() {
    return delActivity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('activity_check/activity/export', {
    ...queryParams.value
  }, `activity_${new Date().getTime()}.xlsx`)
}

getList();
</script>
