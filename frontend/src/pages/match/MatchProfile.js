import React from 'react';
import { Container, Typography, Table, TableContainer, TableHead, TableRow, TableCell, TableBody, Link } from '@mui/material';
import CrownIcon from '@mui/icons-material/EmojiEvents';
import axiosInstance from 'utils/axios';
import { withParams } from 'utils/UtilFunctions';

class MatchProfile extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      match: null,
      name: "",
    };
  }

  componentDidMount() {
    const { name } = this.props.params;
    this.loadMatchProfile(name);
  }

  loadMatchProfile = (name) => {
    axiosInstance.get(`/match/${name}`)
      .then(response => {
          this.setState({
            match: response.data,
            name: name
          });
      })
      .catch(error => {
        console.error("Error loading match profile:", error);
      });
  };

  render() {
    const { match } = this.state;
    return (
      <Container className="page-container">
      <div>
        <Typography variant="h4" gutterBottom className="page-heading">
          Match
        </Typography>
            {match ? (
              <div>
                <Typography variant="subtitle1">Name: {match.name}</Typography>
                <Typography variant="subtitle1">Map: {match.map}</Typography>
                <Typography variant="subtitle1">Start Time: {match.startTimeMatch}</Typography>
                <Typography variant="subtitle1">End Time: {match.endTimeMatch}</Typography>
  
                <Typography variant="h5" gutterBottom display="inline">Team A</Typography>
                <Typography variant="subtitle1" display="inline" style={{ float: "right" }}>Score: {match.teamAScore.finalScore}</Typography>
                <TableContainer>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Player</TableCell>
                        <TableCell>Kills</TableCell>
                        <TableCell>Assists</TableCell>
                        <TableCell>Deaths</TableCell>
                        <TableCell>Headshots</TableCell>
                        <TableCell>MVPS</TableCell>
                        <TableCell>K/D</TableCell>
                        <TableCell>HS%</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {match.teamA.map((player, index) => (
                        <TableRow key={index}>
                            <TableCell>
                                <span>
                                  <Link href={"/profile/" + player.myUser.username} underline="none">
                                    {player.myUser.username}
                                  </Link>
                                  {match.teamLeaderA && match.teamLeaderA.username === player.myUser.username && (
                                      <CrownIcon />
                                  )}
                                </span>
                            </TableCell> 
                            <TableCell>{player.score.kills}</TableCell>
                            <TableCell>{player.score.assists}</TableCell>
                            <TableCell>{player.score.deaths}</TableCell>
                            <TableCell>{player.score.headshots}</TableCell>
                            <TableCell>{player.score.mvps}</TableCell>
                            <TableCell>{player.score.kd}</TableCell>
                            <TableCell>{player.score.hsRate}</TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
  
                <Typography variant="h5" gutterBottom display="inline">Team B</Typography>
                <Typography variant="subtitle1" display="inline" style={{ float: "right" }}>Score: {match.teamBScore.finalScore}</Typography>
                <TableContainer>
                  <Table>
                    <TableHead>
                      <TableRow>
                        <TableCell>Player</TableCell>
                        <TableCell>Kills</TableCell>
                        <TableCell>Assists</TableCell>
                        <TableCell>Deaths</TableCell>
                        <TableCell>Headshots</TableCell>
                        <TableCell>MVPS</TableCell>
                        <TableCell>K/D</TableCell>
                        <TableCell>HS%</TableCell>
                        
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {match.teamB.map((player, index) => (
                        <TableRow key={index} href={"./profile" + player.myUser.username}>
                            <TableCell>
                                <span>
                                  <Link href={"/profile/" + player.myUser.username} underline="none">
                                    {player.myUser.username}
                                  </Link>
                                  {match.teamLeaderB && match.teamLeaderB.username === player.myUser.username && (
                                    <CrownIcon />
                                  )}
                                </span>
                            </TableCell>                        
                            <TableCell>{player.score.kills}</TableCell>
                            <TableCell>{player.score.assists}</TableCell>
                            <TableCell>{player.score.deaths}</TableCell>
                            <TableCell>{player.score.headshots}</TableCell>
                            <TableCell>{player.score.mvps}</TableCell>
                            <TableCell>{player.score.kd}</TableCell>
                            <TableCell>{player.score.hsRate}</TableCell>
                        </TableRow>
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

export default withParams(MatchProfile);
