import React from "react";
import axiosInstance from "utils/axios";
import {Typography, FormControl, InputLabel, Select, MenuItem, Pagination, Container, Table, TableContainer, TableHead, TableRow, TableCell, TableBody  } from "@mui/material"; // Import Pagination component
import UserItem from "pages/user/UserItem";
import "css/styles.css";

class UserHome extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            selectedRole: "all",
            currentPage: 1,
            totalPages: 0,
            pageSize: 10,
            sortBy: "username"
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.selectedRole !== this.state.selectedRole || prevState.currentPage !== this.state.currentPage) {
            this.fetchUsers();
        }
    }

    fetchUsers = () => {
        const { selectedRole, currentPage, pageSize, sortBy } = this.state;
        axiosInstance
            .get("/user", { 
                params: {
                    role: selectedRole,
                    page: currentPage - 1,
                    size: pageSize,
                    sort: sortBy
                }
            })
            .then(res => {
                this.setState({ users: res.data.content, totalPages: res.data.totalPages });
            })
            .catch(error => {
                console.log(error);
            });
    };

    handleRoleChange = (event) => {
        const selectedRole = event.target.value;
        this.setState({ selectedRole, currentPage: 1 }, () => {
        this.fetchUsers();
        });
    };

    handlePageChange = (event, page) => {
        this.setState({ currentPage: page }, () => {
            this.fetchUsers();
        });
    };
    handlePageSizeChange = (event) => {
        this.setState({ pageSize: event.target.value, currentPage: 1 }, () => {
            this.fetchUsers();
        });
    };
    handleSortByChange = (event) => {
        this.setState({ sortBy: event.target.value, currentPage: 1 }, () => {
            this.fetchUsers();
        });
    };
    render() {
        const { users, selectedRole, currentPage, totalPages, pageSize, sortBy } = this.state;
        return (
            <Container className="page-container">
        <div>
          <Typography variant="h4" gutterBottom className="page-heading">
            User Home
          </Typography>
                <FormControl sx={{m: 2}}>
                    <InputLabel id="role-select-label">Role</InputLabel>
                    <Select
                        labelId="role-select-label"
                        id="role-select"
                        value={selectedRole}
                        onChange={this.handleRoleChange}
                    >
                        <MenuItem value="all">All</MenuItem>
                        <MenuItem value="admin">Admin</MenuItem>
                        <MenuItem value="player">Player</MenuItem>
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
                            <MenuItem value="username">Username</MenuItem>
                            <MenuItem value="emailAddress">Email Address</MenuItem>
                            <MenuItem value="firstName">First Name</MenuItem>
                            <MenuItem value="lastName">Last Name</MenuItem>
                        </Select>
                </FormControl>
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
                            {users.map(user => (
                              <UserItem key={user.username} user={user} />
                            ))}
                            </TableBody>
                        </Table>
                </TableContainer>
                <Pagination 
                    count={totalPages} 
                    page={currentPage} 
                    onChange={this.handlePageChange} 
                    size="large" 
                    sort={sortBy}
                    sx={{ mt: 3, display: 'flex', justifyContent: 'center' }}
                />
                </div>
            </Container>
        );
    }
}

export default UserHome;
