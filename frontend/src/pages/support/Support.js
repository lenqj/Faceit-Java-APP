import React, { Component } from 'react';
import { Container, Typography, TextField, Button, Snackbar, MenuItem } from '@mui/material';
import axiosInstance from "utils/axios";

class Support extends Component {
  constructor(props) {
    super(props);
    this.state = {
      recipient: '',
      category: '',
      subject: '',
      body: '',
      snackbarOpen: false,
      snackbarMessage: ''
    };
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const { category, recipient, subject, username, body } = this.state;
    let emailPayload = {
        recipient: recipient,
        category: category,
        subject: subject,
        username: username,
        body: body
    }
    axiosInstance
    .post("/user/sendEmail", emailPayload)
    .then(res => {
        this.setState({
            snackbarMessage: 'Message sent successfully!',
            snackbarOpen: true,
            recipient: '',
            category: '',
            subject: '',
            username: '',
            body: '' 
        });
    })
    .catch(error => {
        this.setState({
            snackbarMessage: 'Failed to send message. Please try again later.',
            snackbarOpen: true
          });
    });
  };

  handleCloseSnackbar = () => {
    this.setState({ 
      snackbarOpen: false 
    });
  };
  render() {
    const { recipient, category, username, subject, body, snackbarOpen, snackbarMessage } = this.state;

    return (
        <Container className="page-container">
          <Typography variant="h4" gutterBottom className="page-heading">
            User Profile
          </Typography>
        <form onSubmit={this.handleSubmit}>
          <TextField
            select
            fullWidth
            label="Category"
            value={category}
            onChange={(e) => this.setState({ category: e.target.value })}
            margin="normal"
            variant="outlined"
            required
          >
            <MenuItem value="Gameplay">Gameplay</MenuItem>
            <MenuItem value="Technical Issues">Technical Issues</MenuItem>
            <MenuItem value="Account Management">Account Management</MenuItem>
            <MenuItem value="Feedback">Feedback</MenuItem>
          </TextField>
          <TextField
            fullWidth
            label="Name"
            value={username}
            onChange={(e) => this.setState({ username: e.target.value })}
            margin="normal"
            variant="outlined"
            required
          />
          <TextField
            fullWidth
            label="Subject"
            value={subject}
            onChange={(e) => this.setState({ subject: e.target.value })}
            margin="normal"
            variant="outlined"
            required
          />
          <TextField
            fullWidth
            label="Email"
            value={recipient}
            onChange={(e) => this.setState({ recipient: e.target.value })}
            margin="normal"
            variant="outlined"
            required
          />
          <TextField
            fullWidth
            label="Message"
            multiline
            rows={4}
            value={body}
            onChange={(e) => this.setState({ body: e.target.value })}
            margin="normal"
            variant="outlined"
            required
          />
          <Button type="submit" variant="contained" color="primary">
            Send
          </Button>
        </form>
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={this.handleCloseSnackbar}
          message={snackbarMessage}
        />
      </Container>
    );
  }
}

export default Support;
