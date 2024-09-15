import React from 'react';
import { Container, Typography, Button, Table, TableBody, TableCell, 
        TableContainer, TableHead, TableRow, FormControl, InputLabel, Select, MenuItem, Pagination} from '@mui/material';
import axiosInstance from 'utils/axios';
import { withParams } from 'utils/UtilFunctions';
import EditIcon from '@mui/icons-material/Edit';
import MatchItem from "pages/match/MatchItem";
import TeamItem from "pages/team/TeamItem";
import "css/styles.css";
import { withAuth } from 'utils/UtilFunctions';

class UserProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
      loggedUser: this.props.auth.user,
      username: "",
      userMatches: [],
      allMaps: [],
      selectedMap: "ALL",
      userTeams: [],
      currentPage: 1,
      totalPages: 0,
      pageSize: 10,
    };
  }

  componentDidMount() {
    const usernameFetch = this.props.params.username;
    this.setState({
      username: usernameFetch,
    },
    () => {
      this.loadAllMaps();
      this.loadUserProfile();
    });
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevState.username !== this.state.username || prevState.selectedMap !== this.state.selectedMap) {
        this.loadUserProfile();
    }
  }

  loadUserProfile = () => {
    const { username } = this.state;
    axiosInstance.get(`/user/${username}`)
      .then(response => {
          this.setState({
            user: response.data,
          });
          this.loadUserMatches();
          this.loadUserTeams();
        })
      .catch(error => {
        console.error("Error loading user profile:", username);
      });
  };

  loadAllMaps = () => {
    axiosInstance.get(`/match/maps`)
      .then(response => {
          this.setState({
            allMaps: response.data,
          });
        })
      .catch(error => {
        console.error("Error loading user profile:", JSON.stringify(this.props));
      });
  };
  loadUserMatches = () => {
    const { username, currentPage, pageSize, selectedMap } = this.state;
    axiosInstance.get(`/user/${username}/matches`, { 
        params: {
            page: currentPage - 1,
            size: pageSize,
            map: selectedMap
        }
      })
      .then(response => {
        this.setState({
          userMatches: response.data.content,
          totalPages: response.data.totalPages 
        });
      })
      .catch(error => {
        console.error("Error loading user matches:", error);
      });
  };
  loadUserTeams = () => {
    const { username, currentPage, pageSize } = this.state;
    axiosInstance.get(`/team`, { 
        params: {
            page: currentPage - 1,
            size: pageSize,
            playerUsername: username
        }
      })
      .then(response => {
        this.setState({
          userTeams: response.data.content,
          totalPages: response.data.totalPages 
        });
      })
      .catch(error => {
        console.error("Error loading user matches:", error);
      });
  };

  handlePageChange = (event, page) => {
    this.setState({ currentPage: page}, () => {
      this.loadUserMatches();
    });
  };

  handlePageSizeChange = (event) => {
    this.setState({ pageSize: event.target.value, currentPage: 1 }, () => {
      this.loadUserMatches();
    });
  };

  handleMapChange = (event) => {
    const selectedMap = event.target.value;
    this.setState({ selectedMap, currentPage: 1 }, () => {
        this.loadUserMatches();
    });
  };

  render() {
    const {user, loggedUser, userMatches, totalPages, currentPage, pageSize, selectedMap, allMaps, userTeams } = this.state
    return (
      <Container className="page-container">
        <div>
          <Typography variant="h4" gutterBottom className="page-heading">
            User Profile
          </Typography>
          {
              user ? 
              (
                <div>
                  <Typography variant="subtitle1">Username: {user.username}</Typography>
                  <Typography variant="subtitle1">First Name: {user.firstName}</Typography>
                  <Typography variant="subtitle1">Last Name: {user.lastName}</Typography>
                  <Typography variant="subtitle1">Email: {user.emailAddress}</Typography>
                  { loggedUser != null && user.username === loggedUser.username ? 
                    (
                    <Button
                        type="submit"
                        variant="contained"
                        color="primary"
                        startIcon={<EditIcon></EditIcon>}
                        href="http://localhost/profile/edit"
                        className="page-button">
                        Edit Profile
                    </Button>
                    ) : 
                    (
                    <Button
                      type="submit"
                      variant="contained"
                      color="primary"
                      startIcon={<EditIcon></EditIcon>}
                      href={`http://localhost/compare/${user.username}`}
                      className="page-button">
                      Compare
                  </Button>
                  )
                  }
                <Typography variant="h5" gutterBottom className="section-heading"> 
                User Matches
              </Typography>
              <FormControl sx={{m: 2}} className="filters">
                        <InputLabel id="pageSize-select-label">Page Size</InputLabel>
                        <Select
                            labelId="pageSize-select-label"
                            id="pageSize-select"
                            value={pageSize}
                            onChange={this.handlePageSizeChange}
                        >
                            <MenuItem value={5}>5</MenuItem>
                            <MenuItem value={10}>10</MenuItem>
                            <MenuItem value={15}>15</MenuItem>
                            <MenuItem value={20}>20</MenuItem>
                        </Select>
              </FormControl>
              <FormControl sx={{m: 2}}>
                    <InputLabel id="map-select-label">Map</InputLabel>
                    <Select
                        labelId="map-select-label"
                        id="map-select"
                        value={selectedMap}
                        onChange={this.handleMapChange}
                        className="map-filter"
                    >
                        <MenuItem value="ALL">ALL</MenuItem>
                        {allMaps.map((option, index) => (
                          <MenuItem key={index + 1} value={option}>
                            {option}
                          </MenuItem>
                        ))}
                    </Select>
                </FormControl>
              <TableContainer className="page-table">
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
                                {userMatches.map((match, index) => (
                                    <MatchItem key={index} match={match} />
                                ))}
                            </TableBody>
                        </Table>
                </TableContainer>

                <Typography variant="h5" gutterBottom className="section-heading">
                User Teams
              </Typography>
              <TableContainer className="page-table">
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Team Leader</TableCell>
                                    <TableCell>Matches</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {userTeams.map((team, index) => (
                                    <TeamItem key={index} team={team} />
                                ))}
                            </TableBody>
                        </Table>
                </TableContainer>
                
                <Pagination 
                    count={totalPages} 
                    page={currentPage} 
                    onChange={this.handlePageChange} 
                    size="large" 
                    sx={{ mt: 3, display: 'flex', justifyContent: 'center' }}
                    className="pagination"
                />
            </div>
              ) 
                : 
              ( 
                <Typography variant="subtitle1">Loading...</Typography>
              )
            }
        </div>
      </Container>
    );
  }
}

export default withAuth(withParams(UserProfile));
