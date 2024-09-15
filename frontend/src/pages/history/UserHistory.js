import React from "react";
import axiosInstance from "utils/axios";
import { Select, FormControl, MenuItem, Pagination, InputLabel, Typography, Container, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";

class UserHistory extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userSessions: [],
            currentPage: 1,
            totalPages: 0,
            pageSize: 3,
            sortBy: "myUser"
        };
    }

    componentDidMount() {
        this.fetchUserSessions();
    }

    handlePageChange = (event, page) => {
        this.setState({ currentPage: page }, () => {
            this.fetchUserSessions();
        });
    };
    handleSortByChange = (event) => {
        this.setState({ sortBy: event.target.value, currentPage: 1 }, () => {
            this.fetchUserSessions();
        });
    };
    handlePageSizeChange = (event) => {
        this.setState({ pageSize: event.target.value, currentPage: 1 }, () => {
            this.fetchUserSessions();
        });
    };

    fetchUserSessions = () => {
        const { currentPage, pageSize, sortBy } = this.state;
        axiosInstance
        .get("/user/sessions", {
            params: {
                page: currentPage - 1,
                size: pageSize,
                sort: sortBy
            }
        })
            .then(res => {
                this.setState({ 
                    userSessions: res.data.content, 
                    totalPages: res.data.totalPages 
                });
            })
            .catch(error => {
                console.log(error);
            });
    };


      render() {
        const { userSessions, currentPage, totalPages, sortBy, pageSize } = this.state;
        return (
            <Container className="page-container">
                <div>
                    <Typography variant="h4" gutterBottom className="page-heading">
                        User Sessions
                    </Typography>

                    <FormControl sx={{m: 2}}>
                        <InputLabel id="sortBy-select-label">Sort By</InputLabel>
                            <Select
                                labelId="sortBy-select-label"
                                id="sortBy-select"
                                value={sortBy}
                                onChange={this.handleSortByChange}
                            >
                            <MenuItem value="myUser">User</MenuItem>
                            <MenuItem value="loginAt">Login</MenuItem>
                            <MenuItem value="logoutAt">Logout</MenuItem>
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
                        <MenuItem value={3}>3</MenuItem>
                        <MenuItem value={5}>5</MenuItem>
                        <MenuItem value={10}>10</MenuItem>
                        <MenuItem value={50}>50</MenuItem>
                    </Select>
                </FormControl>
                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>User</TableCell>
                                    <TableCell>Login Time</TableCell>
                                    <TableCell>Logout Time</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {userSessions.map((session, index) => (
                                    <TableRow key={index}>
                                        <TableCell>{session.myUser.username}</TableCell>
                                        <TableCell>{session.loginAt}</TableCell>
                                        <TableCell>{session.logoutAt}</TableCell>
                                    </TableRow>
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

export default UserHistory;
