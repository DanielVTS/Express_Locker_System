import request from './request';

export const GetCompanyInfo = query => {
    return request({
        url: 'companies/',
        method: 'get',
        params: {
            pagenum: query.pageIndex,
            query: query.name
        }
    });
};

export const ChangeCompanyInfo = form => {
    return request({
        url: 'companies/',
        method: 'put',
        data: form
    });
};

export const DeleteCompanyInfo = id => {
    return request({
        url: 'companies/' + id,
        method: 'DELETE'
    });
};

export const AddCompanyInfo = form => {
    return request({
        url: 'companies/',
        method: 'post',
        data: form
    });
};
