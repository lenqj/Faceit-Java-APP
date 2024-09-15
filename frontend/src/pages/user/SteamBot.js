import React, { Component } from 'react';
import {
    Box,
    Button,
    Container,
    TextField,
    Typography,
} from '@mui/material';
import axiosInstance from 'utils/axiosServer';
import { withAuth } from 'utils/UtilFunctions';

class SteamBot extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData: {
                userId: this.props.auth.user.username,
                username: '',
                password: '',
                sharedSecret: '',
                games: '',
            }
        };
    }

    handleChange = (e) => {
        const { name, value } = e.target;
        this.setState(prevState => ({
            formData: {
                ...prevState.formData,
                [name]: value
            }
        }));
    };

    handleSubmit = async (e) => {
        e.preventDefault();
        const { formData } = this.state;
        let gamesList;
        if (formData.games.includes(',')) {
            gamesList = formData.games.split(',').map(game => parseInt(game.trim(), 10)).join(' ');
        } else {
            gamesList = parseInt(formData.games.trim(), 10);
        }
        try {
            const response = await axiosInstance.post('/addBot', {
                ...formData,
                games: gamesList
            });
            if (response.status === 200) {
                console.log('Bot added successfully.');
            } else {
                console.error('Failed to add bot.');
            }
            window.location.reload();
        } catch (error) {
            console.error('Error adding bot:', error);
            window.location.reload();
        }
    };

    render() {
        const { formData } = this.state;

        return (
            <Container maxWidth="sm">
                <Typography variant="h4" gutterBottom>Add Bot</Typography>
                <form onSubmit={this.handleSubmit}>
                    <TextField
                        fullWidth
                        label="Username"
                        name="username"
                        value={formData.username}
                        onChange={this.handleChange}
                        required
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Password"
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={this.handleChange}
                        required
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Shared Secret"
                        name="sharedSecret"
                        value={formData.sharedSecret}
                        onChange={this.handleChange}
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Games"
                        name="games"
                        value={formData.games}
                        onChange={this.handleChange}
                        margin="normal"
                    />
                    <Box mt={2}>
                        <Button type="submit" variant="contained" color="primary">Add Bot</Button>
                    </Box>
                </form>
            </Container>
        );
    }
}

export default withAuth(SteamBot);
