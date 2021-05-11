import request from './request';

export const GetPackageInfo = query => {
    return request({
        url: 'packageInformation/list',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query:query.uuid,
        }
    });
};
export const GetPackageBoxInfo = query => {
    return request({
        url: 'packageBoxInformation/list',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query: query.uuid,
        }
    });
};

export const GetPackageBoxInfoByCode = query => {
    return request({
        url: 'packageInformation/getPackage',
        method: 'get',
        params: {
            code: query.code,
            phone: query.phone,
        }
    });
};

export const ChangePackageInfo = form => {
    return request({
        url: 'packageInformation/editPackage/',
        method: 'put',
        data: form
    });
};

export const ChangePackageBoxInfo = form => {
    return request({
        url: 'packageBoxInformation/edit/',
        method: 'put',
        data: form
    });
};

export const DeletePackageInfo = id => {
    return request({
        url: 'packageInformation/removePackage/' + id,
        method: 'delete'
    });
};

export const AddPackageInfo = form => {
    return request({
        url: 'packageInformation/postPackage/',
        method: 'post',
        data: form
    });
};

// export const Login = form => {
//     return request({
//         url: 'users/login/',
//         method: 'post',
//         data: form
//     });
// };
//
// export const UpdateAvatar = (id, data) => {
//     return request({
//         url: 'users/'+id+'/avatar',
//         method: 'post',
//         data: data
//     });
// };
