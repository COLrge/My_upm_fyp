<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Related Application ID" prop="applicationId">
        <el-input
          v-model="queryParams.applicationId"
          placeholder="请输入Related Application ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="File Path" prop="filePath">
        <el-input
          v-model="queryParams.filePath"
          placeholder="请输入File Path"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Original Filename" prop="originalFilename">
        <el-input
          v-model="queryParams.originalFilename"
          placeholder="请输入Original Filename"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Upload Time" prop="uploadTime">
        <el-date-picker clearable
          v-model="queryParams.uploadTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择Upload Time">
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
          v-hasPermi="['system:files:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:files:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:files:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['system:files:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="filesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary Key" align="center" prop="id" />
      <el-table-column label="Related Application ID" align="center" prop="applicationId" />
      <el-table-column label="File Type" align="center" prop="fileType" />
      <el-table-column label="File Path" align="center" prop="filePath" />
      <el-table-column label="Original Filename" align="center" prop="originalFilename" />
      <el-table-column label="Upload Time" align="center" prop="uploadTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['system:files:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['system:files:remove']">删除</el-button>
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

    <!-- 添加或修改Visa Application Files对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="filesRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Related Application ID" prop="applicationId">
          <el-input v-model="form.applicationId" placeholder="请输入Related Application ID" />
        </el-form-item>
        <el-form-item label="File Path" prop="filePath">
          <el-input v-model="form.filePath" placeholder="请输入File Path" />
        </el-form-item>
        <el-form-item label="Original Filename" prop="originalFilename">
          <el-input v-model="form.originalFilename" placeholder="请输入Original Filename" />
        </el-form-item>
        <el-form-item label="Upload Time" prop="uploadTime">
          <el-date-picker clearable
            v-model="form.uploadTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择Upload Time">
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

<script setup name="Files">
import { listFiles, getFiles, delFiles, addFiles, updateFiles } from "@/api/system/files";

const { proxy } = getCurrentInstance();

const filesList = ref([]);
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
    applicationId: null,
    fileType: null,
    filePath: null,
    originalFilename: null,
    uploadTime: null
  },
  rules: {
    applicationId: [
      { required: true, message: "Related Application ID不能为空", trigger: "blur" }
    ],
    fileType: [
      { required: true, message: "File Type不能为空", trigger: "change" }
    ],
    filePath: [
      { required: true, message: "File Path不能为空", trigger: "blur" }
    ],
    originalFilename: [
      { required: true, message: "Original Filename不能为空", trigger: "blur" }
    ],
    uploadTime: [
      { required: true, message: "Upload Time不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询Visa Application Files列表 */
function getList() {
  loading.value = true;
  listFiles(queryParams.value).then(response => {
    filesList.value = response.rows;
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
    applicationId: null,
    fileType: null,
    filePath: null,
    originalFilename: null,
    uploadTime: null
  };
  proxy.resetForm("filesRef");
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
  title.value = "添加Visa Application Files";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getFiles(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改Visa Application Files";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["filesRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateFiles(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addFiles(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除Visa Application Files编号为"' + _ids + '"的数据项？').then(function() {
    return delFiles(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/files/export', {
    ...queryParams.value
  }, `files_${new Date().getTime()}.xlsx`)
}

getList();
</script>
