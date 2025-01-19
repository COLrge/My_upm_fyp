<template>
  <div class="statistics-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>Employee Statistics</span>
      </div>
      <el-row>
        <el-table :data="employeeData" style="width: 100%">
          <el-table-column prop="id" label="Employee ID" width="150"></el-table-column>
          <el-table-column prop="name" label="Name" width="200"></el-table-column>
          <el-table-column prop="reviewedCount" label="Reviewed Applications" width="200"></el-table-column>
        </el-table>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div id="employee-pie-chart" style="height: 400px; margin-top: 20px;"></div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "EmployeeStatistics",
  data() {
    return {
      employeeData: [
        { id: "E001", name: "Alice", reviewedCount: 120 },
        { id: "E002", name: "Bob", reviewedCount: 98 },
        { id: "E003", name: "Charlie", reviewedCount: 75 },
      ],
    };
  },
  mounted() {
    this.renderPieChart();
  },
  methods: {
    renderPieChart() {
      const chartDom = document.getElementById("employee-pie-chart");
      const myChart = echarts.init(chartDom);
      const option = {
        title: {
          text: "Reviewed Applications by Employees",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "Applications Reviewed",
            type: "pie",
            radius: "50%",
            data: this.employeeData.map((item) => ({
              value: item.reviewedCount,
              name: item.name,
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      myChart.setOption(option);
    },
  },
};
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}
</style>
