import React from "react";
import {
    Container,
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    Box,
} from '@mui/material';
import axiosInstance from "utils/axiosServer";
import styles from "@chatscope/chat-ui-kit-styles/dist/default/styles.min.css";
import {
    MainContainer,
    ChatContainer,
    MessageList,
    Message,
    MessageInput,
} from "@chatscope/chat-ui-kit-react";
import io from 'socket.io-client';
import SteamBot from './SteamBot';
import { withAuth } from 'utils/UtilFunctions';


class UserSteamAccounts extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            messages: [],
            input: '',
            selectedFriend: null,
            socketGlobal: null,
            friends: [],
            showFriendsList: false,
            bots: [],
            userId: this.props.auth.user.username,
            isAddBotFormOpen: false
        };
    }

    componentDidMount() {
        this.getBotsList();
    }

    componentWillUnmount() {
        if (this.state.socketGlobal) {
            this.state.socketGlobal.disconnect();
        }
    }

    getFriendsList = (selectedBot) => {
        console.log(selectedBot);
        axiosInstance.get(`/getFriends/${selectedBot}`, {
            params: {
                userId: this.state.userId
            }
        })
            .then(response => {
                this.setState({
                    friends: response.data,
                    showFriendsList: true
                });
            })
            .catch(error => {
                this.setState({
                    showFriendsList: false
                });
                console.error('Error fetching friends list:', error);
            });
    };

    getBotsList = () => {
        axiosInstance.get('/getAllBots', {
            params: {
                userId: this.state.userId
            }
        })
            .then(response => {
                const botsList = response.data;
                this.setState({ bots: botsList });
            })
            .catch(error => {
                console.error('Error fetching bots list:', error);
            });
    };

    selectFriend = friendId => {
        this.setState({
            selectedFriend: friendId,
            showFriendsList: false
        });

        const { userId, selectedBot, socketGlobal } = this.state;
        const newChannel = `${userId}-${selectedBot}-${friendId}`;

        if (socketGlobal) {
            socketGlobal.disconnect();
        }

        const newSocket = io('http://135.125.237.153:3000');

        


        newSocket.on('connect', () => {
            console.log('Connected to Socket.IO server');
            newSocket.emit('join', newChannel);
        });
        newSocket.on('message', (data) => {
            try {
                console.log(data);
                const message = JSON.parse(data);
                this.setState(prevState => ({
                    messages: [...prevState.messages, message]
                }));
            } catch (error) {
                console.error('Error parsing Socket.IO message:', error);
            }
        });

        newSocket.on('disconnect', () => {
            console.log('Disconnected from Socket.IO server');
        });

        newSocket.on('error', (error) => {
            console.error('Socket.IO error:', error);
        });

        this.setState({ socketGlobal: newSocket });
    };

    selectBot = botId => {
        this.setState({ 
            selectedBot: botId,
            selectedFriend: null,
            messages: [],
            isAddBotFormOpen: false,
            isAddBotButtonOpen: false
         });
        this.getFriendsList(botId);

    };

    sendMessage = () => {
        const { input, socketGlobal, selectedFriend, selectedBot, userId } = this.state;
        if (input.trim() !== '' && socketGlobal && selectedFriend && selectedBot) {
            const newMessage = {
                botUsername: selectedBot,
                steamId: selectedFriend,
                message: input,
                userId: userId,
                from: 'me'
            };
            this.setState(prevState => ({
                input: ''
            }));
            socketGlobal.emit('message', JSON.stringify(newMessage));
        }
    };

    startBot = botId => {
        axiosInstance.get(`/startBot/${botId}`, {
            params: {
                userId: this.state.userId
            }
        })
            .then(response => {
                this.getBotsList();
            })
            .catch(error => {
                console.error(`Error starting bot ${botId}:`, error);
            });
    };

    stopBot = botId => {
        axiosInstance.get(`/stopBot/${botId}`, {
            params: {
                userId: this.state.userId
            }
        })
            .then(response => {
                this.getBotsList();
            })
            .catch(error => {
                console.error(`Error stopping bot ${botId}:`, error);
            });
    };
    deleteBot = botId => {
        axiosInstance.delete(`/deleteBot/${botId}`, {
            params: {
                userId: this.state.userId
            }
        })
            .then(response => {
                this.getBotsList();
            })
            .catch(error => {
                console.error(`Error stopping bot ${botId}:`, error);
            });
    };

    handleInputChange = value => {
        this.setState({ input: value });
    };

    toggleAddBotForm = () => {
        this.setState(prevState => ({
            isAddBotFormOpen: !prevState.isAddBotFormOpen,
            selectedFriend: null,
            selectedBot: null
        }));
        this.getBotsList();
    };

    render() {
        const { messages, input, selectedFriend, selectedBot, friends, showFriendsList, bots, isAddBotFormOpen} = this.state;

        return (
            <Container>
                <Typography variant="h4" gutterBottom>Chat Room</Typography>
                <TableContainer component={Paper}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Bot ID</TableCell>
                                <TableCell>Status</TableCell>
                                <TableCell>Actions</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {bots === null || bots.length === 0 ? (
                                <TableRow>
                                    <TableCell colSpan={3} align="center">No bots for user</TableCell>
                                </TableRow>
                            ) : (
                                bots.map(bot => (
                                    <TableRow key={bot.botUsername}>
                                        <TableCell>{bot.botUsername}</TableCell>
                                        <TableCell>{bot.status}</TableCell>
                                        <TableCell>
                                            <div style={{ display: 'flex', gap: '8px' }}>
                                                <Button variant="contained" color="success" onClick={() => this.startBot(bot.botUsername)}>
                                                    Start
                                                </Button>
                                                <Button variant="contained" color="error" onClick={() => this.stopBot(bot.botUsername)}>
                                                    Stop
                                                </Button>
                                                <Button variant="contained" color="primary" onClick={() => this.selectBot(bot.botUsername)}>
                                                    View Chat
                                                </Button>
                                                <Button variant="contained" color="warning" onClick={() => this.deleteBot(bot.botUsername)}>
                                                    Delete
                                                </Button>
                                            </div>
                                        </TableCell>
                                    </TableRow>
                                ))
                            )}
                        </TableBody>
                    </Table>
                </TableContainer>

                <Box mt={2}>
                    <Button variant="contained" color="primary" onClick={this.toggleAddBotForm}>Add Bot</Button>
                    {isAddBotFormOpen && <SteamBot />}
                </Box>

                <Box mt={2}>
                    {showFriendsList && friends.length > 0 ? (
                        <Box>
                            {friends.map(friend => (
                                <Box key={friend} mb={1}>
                                    <Button variant="outlined" onClick={() => this.selectFriend(friend)}>
                                        {friend}
                                    </Button>
                                </Box>
                            ))}
                        </Box>
                    ) : (
                        null
                    )}
                </Box>
                <Box mt={2}>
                    {selectedFriend && selectedBot && (
                        <MainContainer>
                            <ChatContainer>
                                <MessageList>
                                    {messages
                                        .filter(message => message.steamId === selectedFriend)
                                        .map((message, index) => (
                                            <Message
                                                key={index}
                                                model={{
                                                    message: message.message,
                                                    sentTime: new Date().toLocaleString(),
                                                    sender: message.from === 'me' ? 'Me' : 'Friend',
                                                    direction: message.from === 'me' ? 'outgoing' : 'incoming',
                                                }}
                                            />
                                        ))}
                                </MessageList>
                                <MessageInput
                                    placeholder="Type message here"
                                    value={input}
                                    onChange={val => this.handleInputChange(val)}
                                    onSend={this.sendMessage}
                                />
                            </ChatContainer>
                        </MainContainer>
                    )}
                </Box>
            </Container>
        );
    }
}

export default withAuth(UserSteamAccounts);
