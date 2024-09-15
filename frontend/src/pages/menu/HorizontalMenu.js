import * as React from 'react';
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import Avatar from '@mui/material/Avatar';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { Link } from 'react-router-dom';
import { withAuth } from 'utils/UtilFunctions';
const settings = ['Profile', 'Logout'];

class HorizontalMenu extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedIndex: null,
      loggedIn: !!this.props.auth.user,
      anchorElUser: null
    };
  }

  handleItemClick = (index) => {
    this.setState({ selectedIndex: index });
  };
  
  handleOpenUserMenu = (event) => {
    this.setState({ anchorElUser: event.currentTarget });
  };

  handleCloseUserMenu = () => {
    this.setState({ anchorElUser: null });
  };

  render() {
    const { selectedIndex, loggedIn, anchorElUser } = this.state;
    const user = this.props.auth.user;
    const userProfileUrl = user ? `/profile/${user.username}` : '';
    return (
      <>
        <AppBar position="fixed">
          <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
            <Typography variant="h6" component="div">
              My App
            </Typography>
            <div>
              <Button
                color={selectedIndex === 0 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(0)}
                component={Link}
                to="/"
              >
                Home
              </Button>
              <Button
                color={selectedIndex === 1 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(1)}
                component={Link}
                to="/users"
              >
                Users
              </Button>
              <Button
                color={selectedIndex === 2 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(2)}
                component={Link}
                to="/matches"
              >
                Matches
              </Button>
              <Button
                color={selectedIndex === 3 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(3)}
                component={Link}
                to="/teams"
              >
                Teams
              </Button>
              <Button
                color={selectedIndex === 4 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(4)}
                component={Link}
                to="/support"
              >
                Support
              </Button>
              <Button
                color={selectedIndex === 5 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(5)}
                component={Link}
                to="/history"
              >
                User History
              </Button>

              <Button
                color={selectedIndex === 6 ? "primary" : "inherit"}
                onClick={() => this.handleItemClick(6)}
                component={Link}
                to="/steam-accounts"
              >
                Steam Accounts
              </Button>
  
            </div>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              {loggedIn ? (
                <Box sx={{ flexGrow: 0 }}>
                  <Tooltip title="Open settings">
                    <IconButton onClick={this.handleOpenUserMenu} sx={{ p: 0 }}>
                      <Avatar alt="User Avatar" />
                    </IconButton>
                  </Tooltip>
                  <Menu
                    anchorEl={anchorElUser}
                    open={Boolean(anchorElUser)}
                    onClose={this.handleCloseUserMenu}
                    sx={{ mt: '45px' }}
                  >
                    {settings.map((setting, index) => (
                      <Link key={setting} to={index === 0 ? userProfileUrl : `/${setting.toLowerCase()}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                        <MenuItem onClick={this.handleCloseUserMenu}>
                          <Typography textAlign="center">{setting}</Typography>
                        </MenuItem>
                      </Link>
                    ))}
                  </Menu>
                </Box>
              ) : (
                <Button color="inherit" href="http://localhost/login">Login</Button>
              )}
            </Box>
          </Toolbar>
        </AppBar>
        <Box sx={{ marginTop: '64px' }} />
      </>
    );
  }
}

export default withAuth(HorizontalMenu);
