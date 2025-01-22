<template>
  <div class="statistics-page">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>Employee Statistics</span>
      </div>
      <el-row>
        <el-table :data="employeeData" style="width: 100%" v-loading="loading">
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
import * as echarts from 'echarts'
import {listApplication} from "@/api/system/application.js";
import {getUser} from "@/api/system/user.js";

export default {
  name: 'EmployeeStatistics',
  data() {
    return {
      loading: false,
      employeeData: [],
      applicationList: [],
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 获取并处理数据
    async fetchData() {
      try {
        this.loading = true
        // 1. 获取所有申请列表
        const response = await listApplication({})
        this.applicationList = response.rows || []

        // 2. 统计每个reviewerId的申请数量
        const reviewerStats = this.calculateReviewerStats()

        // 3. 获取每个reviewer的用户信息
        await this.fetchReviewerNames(reviewerStats)

        // 4. 渲染图表
        await this.$nextTick(() => {
          this.renderPieChart()
        })
      } catch (error) {
        console.error('Error fetching data:', error)
        this.$message.error('Failed to fetch data')
      } finally {
        this.loading = false
      }
    },

    // 计算每个reviewer的统计数据
    calculateReviewerStats() {
      const reviewerCounts = {}

      this.applicationList.forEach(application => {
        const reviewerId = application.reviewerId
        if (reviewerId) {
          reviewerCounts[reviewerId] = (reviewerCounts[reviewerId] || 0) + 1
        }
      })

      return Object.entries(reviewerCounts).map(([reviewerId, count]) => ({
        id: reviewerId,
        reviewedCount: count,
        name: '' // 将在fetchReviewerNames中填充
      }))
    },

    // 获取reviewer的用户名
    async fetchReviewerNames(reviewerStats) {
      try {
        const userPromises = reviewerStats.map(async stat => {
          try {
            const response = await getUser(stat.id)
            // 正确解构response中的data字段
            const userData = response.data
            return {
              ...stat,
              name: userData.nickName || 'Unknown'
            }
          } catch (error) {
            console.error(`Error fetching user ${stat.id}:`, error)
            return {
              ...stat,
              name: 'Unknown'
            }
          }
        })

        this.employeeData = await Promise.all(userPromises)
      } catch (error) {
        console.error('Error fetching user names:', error)
        this.$message.error('Failed to fetch user names')
      }
    },

    // 渲染饼图
    renderPieChart() {
      const chartDom = document.getElementById('employee-pie-chart')
      if (!chartDom) return

      const myChart = echarts.init(chartDom)

      const option = {
        title: {
          text: 'Reviewed Applications by Employees',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          top: 'middle'
        },
        series: [
          {
            name: 'Applications Reviewed',
            type: 'pie',
            radius: '50%',
            data: this.employeeData.map(item => ({
              value: item.reviewedCount,
              name: item.name
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }

      myChart.setOption(option)

      // 添加窗口resize监听
      window.addEventListener('resize', () => {
        myChart.resize()
      })
    }
  },
  // 组件销毁时移除resize监听
  beforeDestroy() {
    window.removeEventListener('resize', this.renderPieChart)
  }
}
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}
</style>