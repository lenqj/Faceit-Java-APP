import React, { Component } from 'react';
import axiosInstance from 'utils/axios';
import { withParams } from 'utils/UtilFunctions';
import { Container, Box, FormControlLabel, Checkbox, Typography } from "@mui/material";
import UserRadar from 'pages/user/UserRadar';
import { withAuth } from 'utils/UtilFunctions';

class UserComparisonPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstPlayer: null,
      secondPlayer: null,
      showFields: {
        kills: true,
        assists: true,
        deaths: true,
        headshots: true,
        mvps: true,
        kd: true,
        hsRate: true,
      }
    };
  }

  componentDidMount() {
    this.loadPlayer1();
  }

  loadPlayer1 = () => {
    const loggedUser = this.props.auth.user;
    if (loggedUser) {
      this.setState({ firstPlayer: loggedUser }, () => {
        this.loadPlayer2();
      });
    }
  };

  loadPlayer2 = () => {
    const { username } = this.props.params;
    axiosInstance.get(`/user/${username}`)
      .then(response => {
        this.setState({ secondPlayer: response.data });
      })
      .catch(error => {
        console.error("Error loading user profile:", error);
      });
  };

  handleToggle = (field) => {
    this.setState(prevState => ({
      showFields: {
        ...prevState.showFields,
        [field]: !prevState.showFields[field]
      }
    }));
  };

  renderComparisonResult = () => {
    const { firstPlayer, secondPlayer, showFields } = this.state;
    return (
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Box sx={{ display: 'flex', flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'center', gap: 2 }}>
          {Object.keys(showFields).map(field => (
            <FormControlLabel
              key={field}
              control={
                <Checkbox
                  checked={showFields[field]}
                  onChange={() => this.handleToggle(field)}
                />
              }
              label={field.charAt(0).toUpperCase() + field.slice(1)}
            />
          ))}
        </Box>
        {firstPlayer && secondPlayer ? (
          <UserRadar
            result={{
              player1: firstPlayer,
              player2: secondPlayer
            }}
            showFields={showFields}
            handleToggle={this.handleToggle} 
          />
        ) : (
          <p>Loading...</p>
        )}
      </Box>
    );
  };

  render() {
    return (
      <Container className="page-container">
      <div>
        <Typography variant="h4" gutterBottom className="page-heading">
          User Compare
        </Typography>
          {this.renderComparisonResult()}
          </div>
      </Container>
    );
  }
}

export default withAuth(withParams(UserComparisonPage));
