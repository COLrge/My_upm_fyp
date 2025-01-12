import request from '@/utils/request'

// 查询Activity list列表
export function listActivity(query) {
  return request({
    url: '/activity_check/activity/list',
    method: 'get',
    params: query
  })
}

// 查询Activity list详细
export function getActivity(id) {
  return request({
    url: '/activity_check/activity/' + id,
    method: 'get'
  })
}

// 新增Activity list
export function addActivity(data) {
  return request({
    url: '/activity_check/activity',
    method: 'post',
    data: data
  })
}

// 修改Activity list
export function updateActivity(data) {
  return request({
    url: '/activity_check/activity',
    method: 'put',
    data: data
  })
}

// 删除Activity list
export function delActivity(id) {
  return request({
    url: '/activity_check/activity/' + id,
    method: 'delete'
  })
}
