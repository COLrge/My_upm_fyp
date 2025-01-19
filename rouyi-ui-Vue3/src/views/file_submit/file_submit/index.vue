<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="File Name" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入File Name"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="User ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入User ID"
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
          v-hasPermi="['file_submit:file_submit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['file_submit:file_submit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['file_submit:file_submit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['file_submit:file_submit:export']"
        >导出</el-button>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['file_submit:file_submit:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['file_submit:file_submit:remove']">删除</el-button>
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

    <!-- 添加或修改File Submission Status Table对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="file_submitRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="File Name" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入File Name" />
        </el-form-item>
        <el-form-item v-if="title !== '添加File Submission Status Table'" label="Submission Status" prop="submitStatus">
          <el-select v-model="form.submitStatus" placeholder="请选择Submission Status">
            <el-option
              v-for="dict in file_submit_state"
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
      { required: true, message: "File Name不能为空", trigger: "blur" }
    ],
    submitStatus: [
      { required: true, message: "Submission Status不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

const idpin = (row, column, cellValue, index) => {
  return row.id + 2025;
};

/** 查询File Submission Status Table列表 */
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
    const response = await listFile_submit(queryParams.value);

    // 检查是否是管理员用户
    if (ADMIN_IDS.includes(currentUserId)) {
      // 管理员可以查看所有记录
      file_submitList.value = response.rows;
      total.value = response.total;
    } else {
      // 普通用户只能查看自己的记录
      const userSubmissions = response.rows.filter(item => BigInt(item.userId) === currentUserId);
      file_submitList.value = userSubmissions;
      total.value = userSubmissions.length;
    }
  } catch (error) {
    console.error('获取提交记录失败:', error);
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

/** generateQR */
function handleGenerateQR(row) {
  const idpinValue = idpin(row); // 调用 idpin 方法获取 idpin 值
  const qrUrl = `http://localhost/submission/QR_submit?pin=${idpinValue}`;
  window.location.href = qrUrl; // 跳转到生成 QR 的页面
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
async function handleAdd() {
  const userStore = useUserStore();
  if (!userStore.id || !userStore.name) {
    await userStore.getInfo();
  }

  try {
    // 获取申请列表
    const response = await listApplication();
    const applications = response.rows || [];

    // 查找当前用户的申请记录
    const application = applications.find(item =>
        String(item.applicantId) === String(userStore.id)
    );
    //console.log('查找的申请记录:', application);

    // 验证状态
    if (application?.status === 'APPROVED') {
      reset();

      // 自动填充当前用户信息
      form.value.userId = userStore.id; // 设置当前用户的 ID
      form.value.userName = userStore.name; // 设置当前用户的用户名
      form.value.submitStatus = "1";

      open.value = true;
      title.value = "添加File Submission Status Table";
    } else {
      ElMessage.error("您的申请状态未被批准，无法进行新增操作");
    }
  } catch (error) {
    console.error('获取申请列表失败:', error);
    ElMessage.error('操作失败，请稍后重试');
  }
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value;

  ElMessageBox.prompt("请输入 PIN 以验证修改权限", "修改验证", {
    inputType: "number",
    confirmButtonText: "确定",
    cancelButtonText: "取消",
  })
      .then(({ value }) => {
        if (parseInt(value) === _id) {
          getFile_submit(_id).then((response) => {
            form.value = response.data;
            open.value = true;
            title.value = "修改 File Submission Status Table";
            ElMessage.success("PIN 验证成功");
          });
        } else {
          ElMessage.error("PIN 不正确，无法进行修改");
        }
      })
      .catch(() => {
        ElMessage.info("已取消修改操作");
      });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["file_submitRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateFile_submit(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addFile_submit(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除File Submission Status Table编号为"' + _ids + '"的数据项？').then(function() {
    return delFile_submit(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('file_submit/file_submit/export', {
    ...queryParams.value
  }, `file_submit_${new Date().getTime()}.xlsx`)
}

getList();
</script>
