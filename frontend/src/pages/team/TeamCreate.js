import React, { Component } from 'react';
import { Typography, Grid, TextField, Button, FormControl, InputLabel, Select, MenuItem, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Container } from '@mui/material';
import axiosInstance from 'utils/axios';

class MatchCreate extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      teamMembers: [],
      teamLeader: '',
      selectedPlayer: null, 
      allPlayers: []
    };
  }

  componentDidMount() {
    axiosInstance.get('/user')
      .then(response => {
        this.setState({ allPlayers: response.data.content });
      })
      .catch(error => {
        console.error('Error fetching players:', error);
      });
  }


  handlePlayerSelectChange = (e) => {
    const username = e.target.value;
    axiosInstance.get(`/user/${username}`)
      .then(response => {
        this.setState({ selectedPlayer: response.data });
      })
      .catch(error => {
        console.error('Error fetching player details:', error);
      });
  };

  


  handleAddPlayerToTeam = () => {
    const { selectedPlayer, teamMembers, allPlayers } = this.state;
    if (selectedPlayer && teamMembers.length < 5) {
      const updatedAllPlayers = allPlayers.filter(player => player.username !== selectedPlayer.username);

      this.setState(prevState => ({
        teamMembers: [...prevState.teamMembers,
        selectedPlayer.username
        ],
        selectedPlayer: null,
        allPlayers: updatedAllPlayers
      }));
    }
  };
  
  
  handleFormSubmit = () => {
    let team = {
      name: this.state.name,
      teamMembers: this.state.teamMembers,
      teamLeader: this.state.teamLeader,
      numberOfMatches: null,
      matches: null
    }
    axiosInstance.post('/team', team)
      .then(response => {
        console.log('Team created successfully:', response.data);
      })
      .catch(error => {
        console.error('Error creating match:', error);
      });
  };

  handleInputChange = (e) => {
    const { name, value } = e.target;
    this.setState({
      [name]: value
    });
  };

  render() {
    const { name, teamMembers, teamLeader, allPlayers, selectedPlayer } = this.state;

    return (
      <Container className="page-container">
      <div>
        <Typography variant="h4" gutterBottom className="page-heading">
          Team Create
        </Typography>
      <Grid>
        <Grid item xs={12}>
          <TextField
            name="name"
            label="Team Name"
            value={name}
            onChange={this.handleInputChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <InputLabel>Select Player for Team A</InputLabel>
            <Select
                value={selectedPlayer ? selectedPlayer : ''}
                onChange={this.handlePlayerSelectChange}
                fullWidth
              >
                {allPlayers.map(player => (
                  <MenuItem key={player.username} value={player.username}>
                    {player.username}
                  </MenuItem>
                ))}
            </Select>
          </FormControl>
          <Button variant="contained" color="primary" onClick={this.handleAddPlayerToTeam}>
            Add Player to Team
          </Button>
          <TableContainer component={Paper}>
  <Table>
    <TableHead>
      <TableRow>
        <TableCell>Player Name</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {teamMembers.map(player => (
        <TableRow key={player}>
          <TableCell>{player}</TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
</TableContainer>
        </Grid>
  <Grid item xs={6}>
    <FormControl fullWidth>
      <InputLabel>Select teamLeader</InputLabel>
      <Select
        value={teamLeader}
        name="teamLeader"
        onChange={this.handleInputChange}
        fullWidth
      >
        {teamMembers.map(player => (
          <MenuItem key={player} value={player}>
            {player}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  </Grid>


  </Grid>

  <Grid item xs={12}>
    <Button variant="contained" color="primary" onClick={this.handleFormSubmit}>
      Create Team
    </Button>
  </Grid>
  </div>
</Container>
    );
  }
}

export default MatchCreate;
