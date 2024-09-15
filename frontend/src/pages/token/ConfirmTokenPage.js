import React from 'react';
import { Container, Typography, CircularProgress, Button } from '@mui/material';
import axiosInstance from 'utils/axios';
import { withParams } from 'utils/UtilFunctions';

class ConfirmTokenPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      requestMade: false,
      tokenExpired: false,
    };
}

  componentDidMount() {
    const { requestMade } = this.state;
    if (!requestMade) {
      this.setState({ requestMade: true });
      axiosInstance
        .get('/token/confirm-account', {
          params: {
            confirmationToken: this.props.params.token,
          },
        })
        .then(() => 
          this.setState({ 
            loading: false,
            message: "Successfully." 
          })
        )
        .catch((error) => {
          if (error.response) {
            const errorResponse = error.response.data;
            if (errorResponse.status === "BAD_REQUEST") {
              this.setState({ 
                loading: false,
                tokenExpired: true,
                message: errorResponse.message
              });
            } else {
              this.setState({
                loading: false,
                message: errorResponse.message || "Server error"
              });
            }
          } else {
            this.setState({
              loading: false,
              message: "Server error"
            });
          }
        });
    }
  }
  

  handleRefetchToken = () => {
    this.setState({ loading: true, message: '' });
    axiosInstance
      .get('/token/resend-token', {
        params: {
        confirmationToken: this.props.params.token,
      }
    })
      .then(response => {
        this.setState({ 
          loading: false, 
          message: "A new token has been sent to your email." 
        });
      })
      .catch(error => {
        if (error.response) {
          const errorResponse = error.response.data;
        this.setState({ 
          loading: false, 
          message: errorResponse.message || "Server error" 
        });
      }
      });
  };

  render() {
    const { loading, tokenExpired } = this.state;
  
    return (
      <Container className="page-container">
        <Typography variant="h4" gutterBottom className="page-heading">
          Confirm Token
        </Typography>
        {loading ? (
          <div style={{ textAlign: 'center' }}>
            <CircularProgress />
          </div>
        ) : tokenExpired ? (
          <div>
            <Typography variant="body1" gutterBottom>
              {this.state.message}
            </Typography>
            <Button variant="contained" color="primary" onClick={this.handleRefetchToken}>
              Refetch Token
            </Button>
          </div>
        ) : (
          <Typography variant="body1" gutterBottom>
            {this.state.message}
          </Typography>
        )}
      </Container>
    );
  }
}

export default withParams(ConfirmTokenPage);
