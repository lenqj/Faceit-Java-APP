import React from "react";
import axiosInstance from "utils/axios";
import { Typography, FormControl, InputLabel, Select, MenuItem, Pagination, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Container } from "@mui/material";
import TeamItem from "pages/team/TeamItem";

class TeamHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            teams: [],
            currentPage: 1,
            totalPages: 0,
            pageSize: 10,
            sortBy: ""
        };
    }

    componentDidMount() {
        this.fetchTeams();
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.selectedMap !== this.state.selectedMap || prevState.currentPage !== this.state.currentPage) {
            this.fetchTeams();
        }
    }

    fetchTeams = () => {
        const { currentPage, pageSize, sortBy } = this.state;
        axiosInstance
            .get("/team", { 
                params: {
                    page: currentPage - 1,
                    size: pageSize,
                    sort: sortBy
                }
            })
            .then(res => {
                this.setState({ teams: res.data.content, totalPages: res.data.totalPages });
            })
            .catch(error => {
                console.log(error);
            });
    };

    handlePageChange = (event, page) => {
        this.setState({ currentPage: page }, () => {
            this.fetchTeams();
        });
    };

    handlePageSizeChange = (event) => {
        this.setState({ pageSize: event.target.value, currentPage: 1 }, () => {
            this.fetchTeams();
        });
    };

    render() {
        const { teams, currentPage, totalPages, pageSize, sortBy } = this.state;
        return (
            <Container className="page-container">
            <div>
              <Typography variant="h4" gutterBottom className="page-heading">
                Team Home
              </Typography>
                <FormControl sx={{m: 2}}>
                    <InputLabel id="pageSize-select-label">Page Size</InputLabel>
                    <Select
                        labelId="pageSize-select-label"
                        id="pageSize-select"
                        value={pageSize}
                        onChange={this.handlePageSizeChange}
                    >
                        <MenuItem value={5}>5</MenuItem>
                        <MenuItem value={10}>10</MenuItem>
                        <MenuItem value={20}>20</MenuItem>
                        <MenuItem value={50}>50</MenuItem>
                    </Select>
                </FormControl>
                <FormControl sx={{m: 2}}>
                    <InputLabel id="sortBy-select-label">Sort by</InputLabel>
                    <Select
                        labelId="sortBy-select-label"
                        id="sortBy-select"
                        value={sortBy}
                        onChange={this.handleSortByChange}
                    >
                        <MenuItem value="name">Name</MenuItem>
                        <MenuItem value="map">Map</MenuItem>
                        <MenuItem value="startTimeMatch">Date</MenuItem>
                    </Select>
                </FormControl>
                <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Team Leader</TableCell>
                                    <TableCell>Matches</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {teams.map((team, index) => (
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
                />
                </div>
            </Container>
        );
    }
}

export default TeamHome;
