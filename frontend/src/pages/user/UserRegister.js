import React from "react";
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import axiosInstance from "utils/axios";
import Grid from "@mui/material/Grid";
import history from 'utils/history';
import {Typography} from "@mui/material";
import {Link} from "react-router-dom";



class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username : "",
            password: "",
            firstName: "",
            lastName: "",
            emailAddress: "",
        };
    }

    handleInput = event => {
        const {value, name} = event.target;
        this.setState({
            [name]: value
        });
        console.log(value);
    };

    onSubmitFunction = event => {
        event.preventDefault();
        let credentials = {
            username : this.state.username,
            password: this.state.password,
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            emailAddress: this.state.emailAddress,
            role: "PLAYER",
            score: {
                kills: 0,
                assists: 0,
                deaths: 0,
                headshots: 0,
                mvps: 0
            }
        }

        axiosInstance.post("/user", credentials)
            .then(
                res => {
                    const val = res.data;
                    this.setState(val);
                    history.push("/users");
                    window.location.reload();
                }
            )
            .catch(error => {
                console.log(error)
            })
    }


    render() {
        return (
            <Container className="page-container">
            <div>
              <Typography variant="h4" gutterBottom className="page-heading">
                User Compare
              </Typography>
                    <Grid container justifyContent="center" alignItems="center" style={{ minHeight: '100vh' }}>
                        <Grid item xs={12} sm={6} md={4}>
            
                            <form onSubmit={this.onSubmitFunction}>
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    required
                                    fullWidth
                                    id="username"
                                    label="Username"
                                    name="username"
                                    autoComplete="username"
                                    onChange={this.handleInput}
                                    autoFocus
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    onChange={this.handleInput}
                                    autoComplete="current-password"
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="emailAddress"
                                    label="Email Address"
                                    name="emailAddress"
                                    type="email"
                                    autoComplete="emailAddress"
                                    onChange={this.handleInput}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="firstName"
                                    label="First Name"
                                    name="firstName"
                                    onChange={this.handleInput}
                                />
                                <TextField
                                    variant="outlined"
                                    margin="normal"
                                    fullWidth
                                    id="lastName"
                                    label="Last Name"
                                    name="lastName"
                                    onChange={this.handleInput}
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    color="primary"
                                >
                                    Create Account
                                </Button>
                                <Grid container justifyContent="center" style={{ marginTop: '20px' }}>
                                <Grid item>
                                    <Link to="/login" style={{ textDecoration: 'none' }}>
                                        <Button variant="text" color="primary">
                                            Already have an account? Login here
                                        </Button>
                                    </Link>
                                </Grid>
                            </Grid>
                            </form>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        );
    }

}

export default Register;