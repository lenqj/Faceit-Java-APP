import React from "react";
import Button from "@mui/material/Button"
import TextField from "@mui/material/TextField";
import Container from "@mui/material/Container";
import axiosInstance from "utils/axios";
import Grid from "@mui/material/Grid";
import {Link} from "react-router-dom";
import Typography from "@mui/material/Typography";
import { withAuth } from "utils/UtilFunctions";
import history from 'utils/history';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
        };
    }

    componentDidMount() {
        this.checkLoggedIn();
    }
    
    checkLoggedIn() {
        const user = this.props.auth.user;;
        if (user == null) {
            history.push("/login");
        }
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
            password: this.state.password
        }

        axiosInstance.post("/login", credentials)
            .then(
                res => {
                    const val = res.data;
                    this.props.auth.login(val);
                    history.push("/profile/" + val.username);
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
                                autoComplete="string"
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
                            />
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                style={{ marginTop: '20px' }}
                            >
                                Sign In
                            </Button>
                            <Grid container justifyContent="center" style={{ marginTop: '20px' }}>
                                <Grid item>
                                    <Link to="/register" style={{ textDecoration: 'none' }}>
                                        <Button variant="text" color="primary">
                                            Don't have an account? Register here
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

export default withAuth(Login);
