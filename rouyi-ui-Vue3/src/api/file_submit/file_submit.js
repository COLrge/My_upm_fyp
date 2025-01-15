import request from '@/utils/request'

// 查询File Submission Status Table列表
export function listFile_submit(query) {
  return request({
    url: '/file_submit/file_submit/list',
    method: 'get',
    params: query
  })
}

// 查询File Submission Status Table详细
export function getFile_submit(id) {
  return request({
    url: '/file_submit/file_submit/' + id,
    method: 'get'
  })
}

// 新增File Submission Status Table
export function addFile_submit(data) {
  return request({
    url: '/file_submit/file_submit',
    method: 'post',
    data: data
  })
}

// 修改File Submission Status Table
export function updateFile_submit(data) {
  return request({
    url: '/file_submit/file_submit',
    method: 'put',
    data: data
  })
}

// 删除File Submission Status Table
export function delFile_submit(id) {
  return request({
    url: '/file_submit/file_submit/' + id,
    method: 'delete'
  })
}
