import React, { Component } from 'react';
import { Typography, Grid, TextField, Button, FormControl, InputLabel, Select, MenuItem, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Container } from '@mui/material';
import axiosInstance from 'utils/axios';

class MatchCreate extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      teamA: [],
      teamB: [],
      teamAScore: {
        firstHalfScore: 0,
        secondHalfScore: 0
      },
      teamBScore: {
        firstHalfScore: 0,
        secondHalfScore: 0
      },
      startTimeMatch: '',
      endTimeMatch: '',
      teamLeaderA: '',
      teamLeaderB: '',
      map: '',
      selectedPlayerA: null, 
      selectedPlayerB: null, 
      allPlayersA: [],
      allPlayersB: [],
      maps: []
    };
  }

  componentDidMount() {
    axiosInstance.get('/match/maps')
      .then(response => {
        this.setState({ maps: response.data });
      })
      .catch(error => {
        console.error('Error fetching maps:', error);
      });
    axiosInstance.get('/user')
      .then(response => {
        this.setState({ allPlayersA: response.data.content, allPlayersB: response.data.content });
      })
      .catch(error => {
        console.error('Error fetching players:', error);
      });
  }


  handlePlayerASelectChange = (e) => {
    const username = e.target.value;
    axiosInstance.get(`/user/${username}`)
      .then(response => {
        this.setState({ selectedPlayerA: response.data });
      })
      .catch(error => {
        console.error('Error fetching player details:', error);
      });
  };

  
  handlePlayerBSelectChange = (e) => {
    const username = e.target.value;
    axiosInstance.get(`/user/${username}`)
      .then(response => {
          this.setState({ selectedPlayerB: response.data });
      })
      .catch(error => {
          console.error('Error fetching player details:', error);
      })
  };


  handleAddPlayerToTeamA = () => {
    const { selectedPlayerA, teamA, allPlayersA, allPlayersB } = this.state;
    if (selectedPlayerA && teamA.length < 5) {
      const updatedAllPlayersA = allPlayersA.filter(player => player.username !== selectedPlayerA.username);
      const updatedAllPlayersB = allPlayersB.filter(player => player.username !== selectedPlayerA.username);

      this.setState(prevState => ({
        teamA: [...prevState.teamA, {
          myUser: selectedPlayerA,
          score: {
            kills: 0,
                assists: 0,
                deaths: 0,
                headshots: 0,
                mvps: 0
          }
        }],
        selectedPlayerA: null,
        allPlayersA: updatedAllPlayersA,
        allPlayersB: updatedAllPlayersB
      }));
    }
  };
  
  handleAddPlayerToTeamB = () => {
    const { selectedPlayerB, teamB, allPlayersB, allPlayersA } = this.state;
    if (selectedPlayerB && teamB.length < 5) {
      const updatedAllPlayersB = allPlayersB.filter(player => player.username !== selectedPlayerB.username);
      const updatedAllPlayersA = allPlayersA.filter(player => player.username !== selectedPlayerB.username);

      this.setState(prevState => ({
        teamB: [...prevState.teamB, {
          myUser: selectedPlayerB,
          score: {
            kills: 0,
                assists: 0,
                deaths: 0,
                headshots: 0,
                mvps: 0
          }
        }],
        selectedPlayerB: null,
        allPlayersB: updatedAllPlayersB,
        allPlayersA: updatedAllPlayersA
      }));
    }
  };
  
  handleFormSubmit = () => {
    let match = {
      name: this.state.name,
      teamA: this.state.teamA,
      teamB: this.state.teamB,
      teamAScore: this.state.teamAScore,
      teamBScore: this.state.teamBScore,
      startTimeMatch: new Date(this.state.startTimeMatch),
      endTimeMatch: new Date(this.state.endTimeMatch),
      teamLeaderA: this.state.teamLeaderA,
      teamLeaderB: this.state.teamLeaderB,
      map: this.state.map
    }
    axiosInstance.post('/match', match)
      .then(response => {
        console.log('Match created successfully:', response.data);
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

  handleTeamAScoreChange = (e) => {
    const { name, value } = e.target;
    this.setState(prevState => ({
      teamAScore: {
        ...prevState.teamAScore,
        [name]: parseInt(value) 
      }
    }));
  };
  
  handleTeamBScoreChange = (e) => {
    const { name, value } = e.target;
    this.setState(prevState => ({
      teamBScore: {
        ...prevState.teamBScore,
        [name]: parseInt(value) 
      }
    }));
  };

  handlePlayerStatChange = (e, team, username, stat) => {
    const { value } = e.target;
    this.setState(prevState => {
      const updatedTeam = prevState[team].map(player => {
        if (player.myUser.username === username) {
          return {
            ...player,
            score: {
              ...player.score,
              [stat]: parseInt(value)
            }
          };
        }
        return player;
      });
      return {
        [team]: updatedTeam
      };
    });
  };
  render() {
    const { name, teamA, teamB, teamLeaderA, teamLeaderB, allPlayersA, allPlayersB, selectedPlayerA, selectedPlayerB, teamAScore, teamBScore, startTimeMatch, endTimeMatch, map, maps } = this.state;

    return (
      <Container className="page-container">
      <div>
        <Typography variant="h4" gutterBottom className="page-heading">
          Match Create
        </Typography>
      <Grid>
        <Grid item xs={12}>
          <TextField
            name="name"
            label="Match Name"
            value={name}
            onChange={this.handleInputChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <InputLabel>Select Player for Team A</InputLabel>
            <Select
                value={selectedPlayerA ? selectedPlayerA.username : ''}
                onChange={this.handlePlayerASelectChange}
                fullWidth
              >
                {allPlayersA.map(player => (
                  <MenuItem key={player.username} value={player.username}>
                    {player.username}
                  </MenuItem>
                ))}
            </Select>
          </FormControl>
          <Button variant="contained" color="primary" onClick={this.handleAddPlayerToTeamA}>
            Add Player to Team A
          </Button>
          <TableContainer component={Paper}>
  <Table>
    <TableHead>
      <TableRow>
        <TableCell>Player Name</TableCell>
        <TableCell>Kills</TableCell>
        <TableCell>Assists</TableCell>
        <TableCell>Deaths</TableCell>
        <TableCell>Headshots</TableCell>
        <TableCell>MVPs</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {teamA.map(player => (
        <TableRow key={player.myUser.username}>
          <TableCell>{player.myUser.username}</TableCell>
          <TableCell>
            <TextField
              name={`teamA[${player.myUser.username}].kills`}
              label="Kills"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.kills}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamA', player.myUser.username, 'kills')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamA[${player.myUser.username}].assists`}
              label="Assists"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.assists}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamA', player.myUser.username, 'assists')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamA[${player.myUser.username}].deaths`}
              label="Deaths"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.deaths}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamA', player.myUser.username, 'deaths')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamA[${player.myUser.username}].headshots`}
              label="Headshots"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.headshots}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamA', player.myUser.username, 'headshots')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamA[${player.myUser.username}].mvps`}
              label="MVPs"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.mvps}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamA', player.myUser.username, 'mvps')}
            />
          </TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
</TableContainer>
        </Grid>
        <Grid item xs={6}>
          <FormControl fullWidth>
            <InputLabel>Select Player for Team B</InputLabel>
            <Select
                value={selectedPlayerB ? selectedPlayerB.username : ''}
                onChange={this.handlePlayerBSelectChange}
                fullWidth
              >
                {allPlayersB.map(player => (
                  <MenuItem key={player.username} value={player.username}>
                    {player.username}
                  </MenuItem>
                ))}
            </Select>
          </FormControl>
          <Button variant="contained" color="primary" onClick={this.handleAddPlayerToTeamB}>
            Add Player to Team B
          </Button>
          <TableContainer component={Paper}>
  <Table>
    <TableHead>
      <TableRow>
        <TableCell>Player Name</TableCell>
        <TableCell>Kills</TableCell>
        <TableCell>Assists</TableCell>
        <TableCell>Deaths</TableCell>
        <TableCell>Headshots</TableCell>
        <TableCell>MVPs</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {teamB.map(player => (
        <TableRow key={player.myUser.username}>
          <TableCell>{player.myUser.username}</TableCell>
          <TableCell>
            <TextField
              name={`teamB[${player.myUser.username}].kills`}
              label="Kills"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.kills}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamB', player.myUser.username, 'kills')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamB[${player.myUser.username}].assists`}
              label="Assists"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.assists}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamB', player.myUser.username, 'assists')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamB[${player.myUser.username}].deaths`}
              label="Deaths"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.deaths}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamB', player.myUser.username, 'deaths')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamB[${player.myUser.username}].headshots`}
              label="Headshots"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.headshots}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamB', player.myUser.username, 'headshots')}
            />
          </TableCell>
          <TableCell>
            <TextField
              name={`teamB[${player.myUser.username}].mvps`}
              label="MVPs"
              type="number"
              variant="outlined"
              size="small"
              value={player.score.mvps}
              onChange={(e) => this.handlePlayerStatChange(e, 'teamB', player.myUser.username, 'mvps')}
            />
          </TableCell>
        </TableRow>
      ))}
    </TableBody>
  </Table>
</TableContainer>
        </Grid>
  <Grid item xs={6}>
    <FormControl fullWidth>
      <InputLabel>Select teamLeaderA</InputLabel>
      <Select
        value={teamLeaderA}
        name="teamLeaderA"
        onChange={this.handleInputChange}
        fullWidth
      >
        {teamA.map(player => (
          <MenuItem key={player.myUser.username} value={player.myUser}>
            {player.myUser.username}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  </Grid>

  <Grid item xs={6}>
    <FormControl fullWidth>
      <InputLabel>Select teamLeaderB</InputLabel>
      <Select
        value={teamLeaderB}
        name="teamLeaderB"
        onChange={this.handleInputChange}
        fullWidth
      >
        {teamB.map(player => (
          <MenuItem key={player.myUser.username} value={player.myUser}>
            {player.myUser.username}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  </Grid>
  <Grid item xs={6}>
          <TextField
            name="firstHalfScore"
            label="Team A First Half Score"
            type="number"
            value={teamAScore.firstHalfScore}
            onChange={this.handleTeamAScoreChange}
            fullWidth
          />
  </Grid>
  <Grid item xs={6}>
    <TextField
      name="secondHalfScore"
      label="Team A Second Half Score"
      type="number"
      value={teamAScore.secondHalfScore}
      onChange={this.handleTeamAScoreChange}
      fullWidth
    />
  </Grid>
  <Grid item xs={6}>
    <TextField
      name="firstHalfScore"
      label="Team B First Half Score"
      type="number"
      value={teamBScore.firstHalfScore}
      onChange={this.handleTeamBScoreChange}
      fullWidth
    />
  </Grid>
  <Grid item xs={6}>
    <TextField
      name="secondHalfScore"
      label="Team B Second Half Score"
      type="number"
      value={teamBScore.secondHalfScore}
      onChange={this.handleTeamBScoreChange}
      fullWidth
    />
  </Grid>
  <Grid item xs={6}>
    <TextField
      name="startTimeMatch"
      label="Start Time"
      type="datetime-local"
      value={startTimeMatch}
      onChange={this.handleInputChange}
      fullWidth
    />
  </Grid>
  <Grid item xs={6}>
    <TextField
      name="endTimeMatch"
      label="End Time"
      type="datetime-local"
      value={endTimeMatch}
      onChange={this.handleInputChange}
      fullWidth
    />
  </Grid>

  <Grid item xs={6}>
    <FormControl fullWidth>
      <InputLabel>Select Map</InputLabel>
      <Select
        value={map}
        name="map"
        onChange={this.handleInputChange}
        fullWidth
      >
        {maps.map(map => (
          <MenuItem key={map} value={map}>
            {map}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  </Grid>

  <Grid item xs={12}>
    <Button variant="contained" color="primary" onClick={this.handleFormSubmit}>
      Create Match
    </Button>
  </Grid>
</Grid>
</div>
</Container>
    );
  }
}

export default MatchCreate;
