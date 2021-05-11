import request from './request';

export const GetLockerInfo = query => {
    return request({
        url: 'lockerBasicInformation/list',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query: query.id
        }
    });
};

export const GetLockerBoxInfo = query => {
    return request({
        url: 'lockerBoxInformation/listBox',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query: query.id
        }
    });
};

export const GetLockerBoxForGet = query => {
    return request({
        url: 'lockerBoxInformation/findBoxForGet',
        method: 'get',
        params: {
            lockerBoxId: query
        }
    });
};

export const GetLockerBoxForPost = query => {
    return request({
        url: 'lockerBoxInformation/findBoxForPost',
        method: 'get',
        params: {
            lockerId: query.lockerId,
            boxType: query.boxType
        }
    });
};


export const ChangeLockerInfo = form => {
    return request({
        url: 'lockerBasicInformation/editLocker/',
        method: 'put',
        data: form
    });
};

export const ChangeLockerBoxInfo = form => {
    return request({
        url: 'lockerBoxInformation/editLockerBox/',
        method: 'put',
        data: form
    });
};

export const DeleteLockerInfo = id => {
    return request({
        url: 'lockerBasicInformation/removeLocker/' + id,
        method: 'delete'
    });
};

export const DeleteLockerBoxInfo = id => {
    return request({
        url: 'lockerBoxInformation/removeLockerBox/' + id,
        method: 'delete'
    });
};

export const AddLockerInfo = form => {
    return request({
        url: 'lockerBasicInformation/addLocker',
        method: 'post',
        data: form
    });
};

export const AddLockerBoxInfo = form => {
    return request({
        url: 'lockerBoxInformation/autoAddLockerBox',
        method: 'post',
        data: form
    });
}

export const AddSingleLockerBoxInfo = form => {
    return request({
        url: 'lockerBoxInformation/addLockerBox',
        method: 'post',
        data: form
    });
}

