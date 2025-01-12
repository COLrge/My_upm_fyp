import request from '@/utils/request'

// 查询Appointment list列表
export function listAppointment_record(query) {
  return request({
    url: '/appointment_record/appointment_record/list',
    method: 'get',
    params: query
  })
}

// 查询Appointment list详细
export function getAppointment_record(id) {
  return request({
    url: '/appointment_record/appointment_record/' + id,
    method: 'get'
  })
}

// 新增Appointment list
export function addAppointment_record(data) {
  return request({
    url: '/appointment_record/appointment_record',
    method: 'post',
    data: data
  })
}

// 修改Appointment list
export function updateAppointment_record(data) {
  return request({
    url: '/appointment_record/appointment_record',
    method: 'put',
    data: data
  })
}

// 删除Appointment list
export function delAppointment_record(id) {
  return request({
    url: '/appointment_record/appointment_record/' + id,
    method: 'delete'
  })
}
