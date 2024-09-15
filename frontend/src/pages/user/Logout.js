import React from "react";
import axiosInstance from "utils/axios";

import history from 'utils/history';
import { withAuth } from "utils/UtilFunctions";


class Logout extends React.Component {

    componentDidMount() {
        this.onSubmitFunction();
    }
    

    onSubmitFunction = () => {
      const { user, logout } = this.props.auth;
        axiosInstance
        .get('/logout', {
          params: {
            username: user.username,
          },
        })
        .then(() => {
            logout();
            history.push("/login");
            window.location.reload();
        }
        ).catch(error => {
            console.log(error)
        })
    }
    render() {
      return null;
  }
}

export default withAuth(Logout);
