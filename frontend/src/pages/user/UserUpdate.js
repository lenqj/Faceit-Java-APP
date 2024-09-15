import React from "react";
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import axiosInstance from "../../utils/axios";
import Grid from "@mui/material/Grid";
import history from '../../utils/history';
import {Typography} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';
import { withAuth } from 'utils/UtilFunctions';


class UserUpdate extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: this.props.auth.user.username,
            role: this.props.auth.user.role,
            password: "",
            firstName: this.props.auth.user.firstName,
            lastName: this.props.auth.user.lastName,
            emailAddress: this.props.auth.user.auth.emailAddress,
            score: this.props.auth.user.score ,
            numberOfMatches: this.props.auth.user.numberOfMatches,
            errors: {
                username: "",
                password: "",
                firstName: "",
                lastName: "",
                emailAddress: ""
            }
        };
    }

    handleInput = event => {
        const {value, name} = event.target;
        this.setState({
            [name]: value
        });
    };

    onSubmitFunction = event => {
        event.preventDefault();
        let credentials = {
            username: this.state.username,
            password: this.state.password,
            role: this.state.role,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            emailAddress: this.state.emailAddress,
            score: this.state.score,
            numberOfMatches: this.state.numberOfMatches
        }
        axiosInstance.put("/user", credentials)
            .then(
                res => {
                    const updatedUser = res.data;
                    localStorage.setItem("user", JSON.stringify(updatedUser));
                    history.push("/profile/" + updatedUser.username);
                    window.location.reload();
                }
            )
            .catch(error => {
                if (error.response) {
                    const errors =  error.response.data;
                    this.setState({
                        errors: {
                            username: errors.username,
                            password: errors.password,
                            firstName: errors.firstName,
                            lastName: errors.lastName,
                            emailAddress: errors.emailAddress
                        }
                    });
                }   
            })
    }
    render() {
            return (
                <Container className="page-container">
                <div>
                  <Typography variant="h4" gutterBottom className="page-heading">
                    User Compare
                  </Typography>
                        <Grid>
                            <form onSubmit={this.onSubmitFunction}>
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="username"
                                    label="Username"
                                    type = "text"
                                    name="username"
                                    aria-readonly
                                    value={this.state.username}
                                    error={!!this.state.errors.username}
                                    helperText={this.state.errors.username}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    value={this.state.password}
                                    onChange={this.handleInput}
                                    error={!!this.state.errors.password}
                                    helperText={this.state.errors.password}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="emailAddress"
                                    label="Email Address"
                                    name="emailAddress"
                                    value={this.state.emailAddress}
                                    onChange={this.handleInput}
                                    error={!!this.state.errors.emailAddress}
                                    helperText={this.state.errors.emailAddress}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
                                    name="firstName"
                                    value={this.state.firstName}
                                    onChange={this.handleInput}
                                    error={!!this.state.errors.firstName}
                                    helperText={this.state.errors.firstName}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="lastName"
                                    label="Last Name"
                                    name="lastName"
                                    value={this.state.lastName}
                                    onChange={this.handleInput}
                                    error={!!this.state.errors.lastName}
                                    helperText={this.state.errors.lastName}
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    startIcon={<EditIcon></EditIcon>}
                                >
                                    Update
                                </Button>
                            </form>
                        </Grid>
                    </div>
                </Container>
            );
    }

}

export default withAuth(UserUpdate);
