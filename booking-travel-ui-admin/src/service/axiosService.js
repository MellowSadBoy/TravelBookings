import Axios from "axios";

class AxiosService {
  axios;
  axiosConfig;
  authService;
  constructor() {
    this.axios = Axios.create({
      baseURL: this.getBaseUrl(),
    });
    this.getAxiosConfig();
  }

  getBaseUrl() {
    return "http://localhost:8264";
  }

  addOption(config) {
    this.axiosConfig = {
      ...config,
    };
  }

  getAxiosConfig = () => {};

  removeAxiosConfig = () => {
    this.axiosConfig = {
      headers: {
        iKeapy: ``,
        "Content-Type": "application/json",
      },
    };
  };

  getMethod(uri, loading = true) {
    return this.handleFlow(this.axios.get(uri, this.axiosConfig), loading);
  }

  postMethod(uri, data, loading = true) {
    return this.handleFlow(
      this.axios.post(uri, data, this.axiosConfig),
      loading
    );
  }

  putMethod(uri, data, loading = true) {
    return this.handleFlow(
      this.axios.put(uri, data, this.axiosConfig),
      loading
    );
  }

  patchMethod(uri, data, loading = true) {
    return this.handleFlow(
      this.axios.patch(uri, data, this.axiosConfig),
      loading
    );
  }

  deleteMethod(uri, loading = true) {
    return this.handleFlow(this.axios.delete(uri, this.axiosConfig), loading);
  }

  handleFlow(method, loading = true) {
    return new Promise((resolve, reject) => {
      method
        .then((res) => {
          resolve({
            data: res.data,
            status: res.status,
            isSuccess: true,
          });
        })
        .catch((err) => {
          this.handleError(err);
          reject({
            err: err,
          });
        });
    });
  }

  handleError = (err) => {
    const status = err.response?.status;
    switch (status) {
      case 401:
      case 403:
        window.location.assign("/error");
        break;
      case 500:
        break;
      default:
        // window.location.assign("/error");
        break;
    }
  };
  //
  axiosInstance = (req) => {
    this.axios(req, this.axiosConfig);
  };
}

const AxiosServ = new AxiosService();
export default AxiosServ;
