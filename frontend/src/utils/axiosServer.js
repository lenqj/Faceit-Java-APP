import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://135.125.237.153:3000",
    headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers":
            "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
    }
});

export default axiosInstance;