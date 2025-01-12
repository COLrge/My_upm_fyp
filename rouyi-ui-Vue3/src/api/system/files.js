import request from '@/utils/request'

// 查询Visa Application Files列表
export function listFiles(query) {
  return request({
    url: '/system/files/list',
    method: 'get',
    params: query
  })
}

// 查询Visa Application Files详细
export function getFiles(id) {
  return request({
    url: '/system/files/' + id,
    method: 'get'
  })
}

// 新增Visa Application Files
export function addFiles(data) {
  return request({
    url: '/system/files',
    method: 'post',
    data: data
  })
}

// 修改Visa Application Files
export function updateFiles(data) {
  return request({
    url: '/system/files',
    method: 'put',
    data: data
  })
}

// 删除Visa Application Files
export function delFiles(id) {
  return request({
    url: '/system/files/' + id,
    method: 'delete'
  })
}
