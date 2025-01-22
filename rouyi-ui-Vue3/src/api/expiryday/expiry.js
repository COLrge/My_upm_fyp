// 导入 request 模块，用于发起网络请求
import request from '@/utils/request';

// 查询 store_expiryday 列表
export function listExpiry(query) {
  return request({
    url: '/expiryday/expiry/list',
    method: 'get',
    params: query
  });
}

// 查询 store_expiryday 详细
export function getExpiry(id) {
  return request({
    url: '/expiryday/expiry/' + id,
    method: 'get'
  });
}

// 新增 store_expiryday
export function addExpiry(data) {
  return request({
    url: '/expiryday/expiry',
    method: 'post',
    data: data
  });
}

// 修改 store_expiryday
export function updateExpiry(data) {
  return request({
    url: '/expiryday/expiry',
    method: 'put',
    data: data
  });
}

// 删除 store_expiryday
export function delExpiry(id) {
  return request({
    url: '/expiryday/expiry/' + id,
    method: 'delete'
  });
}

// 根据用户 ID 查询签证过期时间
export function getVisaExpiryDateByUserId(userId) {
  return request({
    url: '/expiryday/expiry/visaExpiryDate/' + userId, // 这里的 URL 应该与后端的接口路径相匹配
    method: 'get'
  });
}