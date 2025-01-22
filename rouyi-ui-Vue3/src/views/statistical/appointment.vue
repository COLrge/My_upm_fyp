<template>
  <div class="statistics-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>Recent 5 Days Statistics</span>
      </div>
      <el-row>
        <!-- 表格 -->
        <el-table :data="appointmentData" style="width: 100%; margin-bottom: 20px" v-loading="loading">
          <el-table-column prop="activityId" label="Activity ID" width="150"></el-table-column>
          <el-table-column prop="date" label="Date" width="200"></el-table-column>
          <el-table-column prop="count" label="Number of Appointments" width="200"></el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <!-- 柱状图 -->
        <el-col :span="24">
          <div id="appointment-bar-chart" style="height: 400px;"></div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { listAppointment_record } from "@/api/appointment_record/appointment_record";
import { getActivity } from "@/api/activity_check/activity";

export default {
  name: "AppointmentStatistics",
  data() {
    return {
      loading: false,
      appointmentData: [], // 表格显示的所有数据
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        this.loading = true;
        // 获取预约记录列表
        const appointmentResponse = await listAppointment_record({});
        // 使用rows字段获取数据数组
        const appointments = appointmentResponse?.rows || [];

        if (!Array.isArray(appointments)) {
          console.error("Unexpected response format:", appointmentResponse);
          this.$message.error('Failed to get appointment data');
          return;
        }

        // 按 activityId 分类并统计 number of appointments
        const activityMap = {};
        appointments.forEach((app) => {
          const activityId = app.activityId;
          if (activityId) {
            if (!activityMap[activityId]) {
              activityMap[activityId] = { activityId, count: 0, date: "" };
            }
            activityMap[activityId].count++;
          }
        });

        // 获取每个 activityId 对应的 openTime
        const activityPromises = Object.keys(activityMap).map(async (activityId) => {
          try {
            const activityResponse = await getActivity(activityId);
            const activity = activityResponse?.data;
            if (activity) {
              activityMap[activityId].date = this.formatDate(activity.openTime) || "Unknown";
              return activityMap[activityId];
            }
          } catch (error) {
            console.error(`Error fetching activity ${activityId}:`, error);
            activityMap[activityId].date = "Unknown";
            return activityMap[activityId];
          }
        });

        // 等待所有活动数据获取完成
        const results = await Promise.all(activityPromises);

        // 过滤掉undefined的结果
        this.appointmentData = results.filter(Boolean)
            // 按日期排序，最近5天
            .sort((a, b) => new Date(b.date) - new Date(a.date))
            .slice(0, 5);

        // 确保数据已经正确绑定到表格
        console.log("Processed appointmentData:", this.appointmentData);

        // 渲染柱状图
        this.$nextTick(() => {
          this.renderBarChart();
        });
      } catch (error) {
        console.error("Failed to fetch data:", error);
        this.$message.error('Failed to load statistics');
      } finally {
        this.loading = false;
      }
    },

    formatDate(dateString) {
      if (!dateString) return "Unknown";
      try {
        const date = new Date(dateString);
        return date.toLocaleDateString();
      } catch (error) {
        console.error("Date formatting error:", error);
        return dateString;
      }
    },

    renderBarChart() {
      const chartDom = document.getElementById("appointment-bar-chart");
      if (!chartDom) return;

      const myChart = echarts.init(chartDom);
      const option = {
        title: {
          text: "Appointments by Activity",
          left: "center",
        },
        tooltip: {
          trigger: "axis",
          formatter: (params) => {
            const data = params[0].data;
            return `Activity ID: ${this.appointmentData[params[0].dataIndex].activityId}<br>Date: ${params[0].name}<br>Appointments: ${data}`;
          },
        },
        xAxis: {
          type: "category",
          data: this.appointmentData.map((item) => item.date),
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        yAxis: {
          type: "value",
          name: "Number of Appointments",
        },
        series: [
          {
            data: this.appointmentData.map((item) => item.count),
            type: "bar",
            barWidth: "50%",
            itemStyle: {
              color: "#409EFF",
            },
          },
        ],
      };

      myChart.setOption(option);

      // 添加窗口resize监听
      window.addEventListener('resize', () => {
        myChart.resize();
      });
    },
  },
  // 组件销毁时清理
  beforeDestroy() {
    window.removeEventListener('resize', () => {
      const chartDom = document.getElementById("appointment-bar-chart");
      if (chartDom) {
        echarts.getInstanceByDom(chartDom)?.dispose();
      }
    });
  }
};
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}
</style>