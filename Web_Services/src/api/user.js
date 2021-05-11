import request from './request';

export const GetUserInfo = query => {
    return request({
        url: 'account/list/',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query: query.username,
        }
    });
};

export const ChangeUserInfo = form => {
    return request({
        url: 'account/update',
        method: 'put',
        data: form
    });
};

export const ChangeUserPassword = form => {
    return request({
        url: 'account/updatePassword',
        method: 'put',
        data: form
    });
};

export const DeleteUserInfo = id => {
    return request({
        url: 'account/delete/' + id,
        method: 'delete'
    });
};

export const AddUserInfo = form => {
    return request({
        url: 'account/register',
        method: 'post',
        data: form
    });
};

export const Login = form => {
    return request({
        url: 'account/login',
        method: 'post',
        data: form
    });
};

export const UpdateAvatar = (id, data) => {
    return request({
        url: 'users/' + id + '/avatar',
        method: 'post',
        data: data
    });
};
