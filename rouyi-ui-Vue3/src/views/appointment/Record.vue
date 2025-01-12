<template>
  <div class="app">
    <el-container>
      <el-header>
        <h2>预约系统</h2>
      </el-header>
      <el-main>
        <!-- 预约表单 -->
        <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="appointment-form">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="日期" prop="date">
            <el-date-picker v-model="form.date" type="date" placeholder="选择日期" />
          </el-form-item>
          <el-form-item label="时间" prop="time">
            <el-time-picker v-model="form.time" placeholder="选择时间" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="onReset">重置</el-button>
          </el-form-item>
        </el-form>

        <!-- 预约列表 -->
        <el-table :data="appointments" style="width: 100%; margin-top: 20px;" border>
          <el-table-column prop="name" label="姓名" width="180" />
          <el-table-column prop="date" label="日期" width="180" />
          <el-table-column prop="time" label="时间" width="180" />
        </el-table>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';

const form = reactive({
  name: '',
  date: '',
  time: '',
});

const appointments = reactive([]);
const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  date: [{ required: true, message: '请选择日期', trigger: 'change' }],
  time: [{ required: true, message: '请选择时间', trigger: 'change' }],
};

const formRef = ref(null);

const onSubmit = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      appointments.push({ ...form });
      onReset();
    }
  });
};

const onReset = () => {
  formRef.value.resetFields();
};
</script>

<style>
.app {
  margin: 20px;
}
.appointment-form {
  max-width: 400px;
  margin: 0 auto;
}
</style>
