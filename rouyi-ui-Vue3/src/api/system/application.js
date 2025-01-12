import request from '@/utils/request'

// 查询Visa Application Form列表
export function listApplication(query) {
  return request({
    url: '/system/application/list',
    method: 'get',
    params: query
  })
}

// 查询Visa Application Form详细
export function getApplication(id) {
  return request({
    url: '/system/application/' + id,
    method: 'get'
  })
}

// 新增Visa Application Form
export function addApplication(data) {
  return request({
    url: '/system/application',
    method: 'post',
    data: data
  })
}

// 修改Visa Application Form
export function updateApplication(data) {
  return request({
    url: '/system/application',
    method: 'put',
    data: data
  })
}

// 删除Visa Application Form
export function delApplication(id) {
  return request({
    url: '/system/application/' + id,
    method: 'delete'
  })
}

// 分配审核人员
export function assignReviewer(id, reviewerId) {
  return request({
    url: `/system/application/assign/${id}`,
    method: 'put',
    params: { reviewerId }
  })
}

// 审核签证申请
export function reviewApplication(id, status, remarks) {
  return request({
    url: `/system/application/review/${id}`,
    method: 'put',
    params: {
      status,
      remarks
    }
  })
}
// getMySelfReviewer
export function getMySelfReviewer() {
  return request({
    url: `/system/application/getMySelfReviewer/`,
    method: 'get'
  })
}
