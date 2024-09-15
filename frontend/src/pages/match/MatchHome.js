import React from "react";
import axiosInstance from "utils/axios";
import { Typography, FormControl, InputLabel, Select, MenuItem, Pagination, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Container } from "@mui/material";
import MatchItem from "pages/match/MatchItem";

class MatchHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            matches: [],
            allMaps: [],
            selectedMap: "ALL",
            currentPage: 1,
            totalPages: 0,
            pageSize: 10,
            sortBy: "name"
        };
    }

    componentDidMount() {
        this.fetchMatches();
        this.loadAllMaps();
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.selectedMap !== this.state.selectedMap || prevState.currentPage !== this.state.currentPage) {
            this.fetchMatches();
        }
    }

    fetchMatches = () => {
        const { selectedMap, currentPage, pageSize, sortBy } = this.state;
        axiosInstance
            .get("/match", { 
                params: {
                    map: selectedMap,
                    page: currentPage - 1,
                    size: pageSize,
                    sort: sortBy
                }
            })
            .then(res => {
                this.setState({ matches: res.data.content, totalPages: res.data.totalPages });
            })
            .catch(error => {
                console.log(error);
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

    handleMapChange = (event) => {
        const selectedMap = event.target.value;
        this.setState({ selectedMap, currentPage: 1 }, () => {
            this.fetchMatches();
        });
    };

    handlePageChange = (event, page) => {
        this.setState({ currentPage: page }, () => {
            this.fetchMatches();
        });
    };

    handlePageSizeChange = (event) => {
        this.setState({ pageSize: event.target.value, currentPage: 1 }, () => {
            this.fetchMatches();
        });
    };

    handleSortByChange = (event) => {
        this.setState({ sortBy: event.target.value, currentPage: 1 }, () => {
            this.fetchMatches();
        });
    };

    render() {
        const { matches, selectedMap, currentPage, totalPages, pageSize, sortBy, allMaps } = this.state;
        return (
            <Container className="page-container">
            <div>
              <Typography variant="h4" gutterBottom className="page-heading">
                Match Home
              </Typography>
                <FormControl sx={{m: 2}}>
                    <InputLabel id="map-select-label">Map</InputLabel>
                    <Select
                        labelId="map-select-label"
                        id="map-select"
                        value={selectedMap}
                        onChange={this.handleMapChange}
                    >
                        <MenuItem value="ALL">ALL</MenuItem>
                        {allMaps.map((option, index) => (
                          <MenuItem key={index + 1} value={option}>
                            {option}
                          </MenuItem>
                        ))}
                    </Select>
                </FormControl>
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
                                    <TableCell>Date</TableCell>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Score</TableCell>
                                    <TableCell>Map</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {matches.map((match, index) => (
                                    <MatchItem key={index} match={match} />
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

export default MatchHome;
