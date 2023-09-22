import axios from "axios"


const setConfigApi = _ => {
    let url = 'http://localhost:8080/api/customer-feedback';
    
    axios.defaults.baseURL = url;
}

const useFetch = async ({path, method='get', data={}}) => {
    setConfigApi();
    
    return new Promise((resolve, reject) => {
        axios[method](path, data)
        .then(resolve)
        .catch(reject)
    });
}

export {useFetch}