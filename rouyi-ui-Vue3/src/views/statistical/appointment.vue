<template>
  <div class="statistics-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>Recent 5 Days Statistics</span>
      </div>
      <el-row>
        <el-col :span="24">
          <!-- Display the statistics of all 5 days -->
          <ul class="info-list">
            <li v-for="(item, index) in appointmentData" :key="index">
              <strong>{{ formatDate(item.time) }}</strong>:
              {{ item.count }} appointments
            </li>
          </ul>
          <!-- Bar chart for appointment statistics -->
          <div id="appointment-bar-chart" style="height: 400px;"></div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "AppointmentStatistics",
  data() {
    return {
      // Data for the last 5 days
      appointmentData: [
        { time: "2025-01-20", count: 45 },
        { time: "2025-01-21", count: 60 },
        { time: "2025-01-22", count: 35 },
        { time: "2025-01-23", count: 50 },
        { time: "2025-01-24", count: 70 },
      ],
    };
  },
  mounted() {
    this.renderBarChart();
  },
  methods: {
    renderBarChart() {
      const chartDom = document.getElementById("appointment-bar-chart");
      const myChart = echarts.init(chartDom);
      const option = {
        title: {
          text: "Recent 5 Days Appointments",
          left: "center",
        },
        tooltip: {
          trigger: "axis",
          formatter: (params) => {
            const data = params[0].data;
            return `Date: ${params[0].name}<br>Appointments: ${data}`;
          },
        },
        xAxis: {
          type: "category",
          data: this.appointmentData.map((item) => item.time),
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
    },
    formatDate(dateStr) {
      const date = new Date(dateStr);
      return date.toLocaleDateString("en-US", {
        month: "long",
        day: "numeric",
      });
    },
  },
};
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}

.info-list {
  margin: 10px 0;
  padding: 0;
  list-style: none;
  font-size: 16px;
  color: #333;
}

.info-list li {
  margin-bottom: 5px;
}
</style>
