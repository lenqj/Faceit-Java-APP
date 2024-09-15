import React from 'react';
import { Container, Typography, Table, TableContainer, TableHead, TableRow, TableCell, TableBody } from '@mui/material';
import axiosInstance from 'utils/axios';
import { withParams } from 'utils/UtilFunctions';
import MatchItem from "pages/match/MatchItem";
import UserItem from "pages/user/UserItem";


class TeamProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      team: null,
      name: "",
    };
  }

  componentDidMount() {
    const { name } = this.props.params;
    this.loadMatchProfile(name);
  }

  loadMatchProfile = (name) => {
    axiosInstance.get(`/team/${name}`)
      .then(response => {
          this.setState({
            team: response.data,
            name: name
          });
      })
      .catch(error => {
        console.error("Error loading match profile:", error);
      });
  };

  render() {
    const { team } = this.state;
    return (
      <Container className="page-container">
      <div>
        <Typography variant="h4" gutterBottom className="page-heading">
          Team
        </Typography>
            {team ? (
              <div>
                <Typography variant="subtitle1">Name: {team.name}</Typography>
                <Typography variant="subtitle1">Team Leader: {team.teamLeader.username}</Typography>
                <Typography variant="subtitle1">Number of matches: {team.numberOfMatches}</Typography>
  
                <Typography variant="h5" gutterBottom display="inline">Players in team</Typography>
                <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Avatar</TableCell>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Email Address</TableCell>
                                    <TableCell>Full Name</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {team.teamMembers.map(user => (
                              <UserItem key={user.username} user={user} />
                            ))}
                            </TableBody>
                        </Table>
                </TableContainer>

                <Typography variant="h5" gutterBottom display="inline">Matches as team</Typography>
                <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Date</TableCell>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Score</TableCell>
                                    <TableCell>Map</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {team.matches.map((match, index) => (
                                    <MatchItem key={index} match={match} />
                                ))}
                            </TableBody>
                        </Table>
                </TableContainer>
  
              </div>
            ) : (
              <Typography variant="subtitle1">Loading...</Typography>
            )}
          </div>
        </Container>
      );
  }
}

export default withParams(TeamProfile);
