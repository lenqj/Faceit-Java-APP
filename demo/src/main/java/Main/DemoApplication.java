/*
package Main;

import Main.DTO.*;
import Main.constants.Map;
import Main.mapper.MyUserMapper;
import Main.mapper.UserRoleMapper;
import Main.model.*;
import Main.repository.MyMatchRepository;
import Main.repository.UserRoleRepository;
import Main.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringBootApplication
@CrossOrigin("http://localhost:80")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	CommandLineRunner init(MyMatchService myMatchService, MyUserService myUserService, UserRoleService userRoleService, MyUserTeamService myUserTeamService, UserRoleMapper userRoleMapper) {
		return args -> {
			UserRole adminRole = UserRole.builder().name("ADMIN").build();
			UserRole playerRole = UserRole.builder().name("PLAYER").build();
			userRoleService.createRole(adminRole);
			userRoleService.createRole(playerRole);
			String[] playerNames = {
					"s1mple", "device", "ZywOo", "NiKo", "electronic",
					"syrsoN", "ropz", "huNter-", "EliGE", "Brollan",
					"Jame", "cadiaN", "stavn", "sergej", "kennyS"
			};
			int i = 0;
			List<MyUserInMatchDTO> myUserInMatches = new ArrayList<>();
				for (String playerName : playerNames) {
					MyUserCreationDTO userCreationDTO = MyUserCreationDTO.builder()
							.username(playerName.toLowerCase())
							.password("password")
							.lastName("Lastname")
							.firstName("Firstname")
							.role(userRoleMapper.userRoleToUserRoleDTO(playerRole))
							.emailAddress(playerName.toLowerCase() + "@gmail.com")
							.build();
					MyUserDTO myUser = myUserService.createUser(userCreationDTO);
					Score userScore = Score.builder()
							.kills(10)
							.assists(10)
							.deaths(3)
							.headshots(5)
							.mvps(2)
							.build();
					MyUserInMatchDTO myUserInMatch = MyUserInMatchDTO.builder()
							.myUser(myUser)
							.score(userScore)
							.build();
					myUserInMatches.add(myUserInMatch);
			}
				*/
/*MyMatchDTO match = MyMatchDTO.builder()
						.name("Match1")
						.teamA(myUserInMatches.subList(0, 1))
						.teamB(myUserInMatches.subList(2, 3))
						.startTimeMatch(new Date())
						.endTimeMatch(new Date())
						.teamLeaderA(myUser)
						.teamLeaderB(myUser)
						.teamAScore(new MyMatchScore())
						.teamBScore(new MyMatchScore())
						.map(Map.values()[0 % 8].toString())
						.build();
				myMatchService.createMatch(match);

				MyUserTeamCreationDTO myUserTeamCreationDTO = MyUserTeamCreationDTO
						.builder()
						.name("test")
						//.matches(List.of("Match1"))
						.teamLeader("s1mple")
						.teamMembers(List.of("s1mple", "niko"))
						.build();


				myUserTeamService.createMyUserTeam(myUserTeamCreationDTO);*//*


		};
	}
}
*/
package Main;

import Main.DTO.*;
import Main.constants.Map;
import Main.mapper.MyUserMapper;
import Main.mapper.UserRoleMapper;
import Main.model.*;
import Main.repository.MyMatchRepository;
import Main.repository.UserRoleRepository;
import Main.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:80")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DemoApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PasswordEncoder bcryptEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner init(MyMatchService myMatchService, MyUserService myUserService, UserRoleService userRoleService, MyUserTeamService myUserTeamService, UserRoleMapper userRoleMapper) {
		return args -> {
			UserRole adminRole = UserRole.builder().name("ADMIN").build();
			UserRole playerRole = UserRole.builder().name("PLAYER").build();
			userRoleService.createRole(adminRole);
			userRoleService.createRole(playerRole);

			List<MyUserInMatchDTO> myUserInMatches = new ArrayList<>();
			String[] playerNames = {
					"s1mple", "device", "ZywOo", "NiKo", "electronic",
					"syrsoN", "ropz", "huNter-", "EliGE", "Brollan",
					"Jame", "cadiaN", "stavn", "sergej", "kennyS"
			};
			for (String playerName : playerNames) {
				MyUserCreationDTO userCreationDTO = MyUserCreationDTO.builder()
						.username(playerName.toLowerCase())
						.password("Password1234!")
						.firstName(playerName.toUpperCase().charAt(0) + "firstname" )
						.lastName(playerName.toUpperCase().charAt(0) + "lastname" )
						.role(userRoleMapper.userRoleToUserRoleDTO(playerRole))
						.emailAddress(playerName.toLowerCase() + "@gmail.com")
						.build();
				MyUserDTO myUser = myUserService.createUser(userCreationDTO);

				Score userScore = Score.builder()
						.kills(10)
						.assists(10)
						.deaths(3)
						.headshots(5)
						.mvps(2)
						.build();
				MyUserInMatchDTO myUserInMatch = MyUserInMatchDTO.builder()
						.myUser(myUser)
						.score(userScore)
						.build();
				myUserInMatches.add(myUserInMatch);

			}
			List<MyMatchDTO> matches = new ArrayList<>();
			String[] matchNames = {"Match1", "Match2", "Match3", "Match4", "Match5", "Match6", "Match7", "Match8", "Match9", "Match10", "Match11", "Match12", "Match13", "Match14", "Match15"};
			Date[] startDates = new Date[]{
					new Date(),
					new Date(System.currentTimeMillis() + 86400000), // mâine
					new Date(System.currentTimeMillis() + 2 * 86400000), // peste 2 zile
					new Date(System.currentTimeMillis() + 3 * 86400000), // peste 3 zile
					new Date(System.currentTimeMillis() + 4 * 86400000), // peste 4 zile
					new Date(System.currentTimeMillis() + 5 * 86400000), // peste 5 zile
					new Date(System.currentTimeMillis() + 6 * 86400000), // peste 6 zile
					new Date(System.currentTimeMillis() + 7 * 86400000), // peste 7 zile
					new Date(System.currentTimeMillis() + 8 * 86400000), // peste 8 zile
					new Date(System.currentTimeMillis() + 9 * 86400000), // peste 9 zile
					new Date(System.currentTimeMillis() + 10 * 86400000), // peste 10 zile
					new Date(System.currentTimeMillis() + 11 * 86400000), // peste 11 zile
					new Date(System.currentTimeMillis() + 12 * 86400000), // peste 12 zile
					new Date(System.currentTimeMillis() + 13 * 86400000), // peste 13 zile
					new Date(System.currentTimeMillis() + 14 * 86400000) // peste 14 zile
			};

			Date[] endDates = new Date[]{
					new Date(System.currentTimeMillis() + 86400000), // mâine
					new Date(System.currentTimeMillis() + 2 * 86400000), // peste 2 zile
					new Date(System.currentTimeMillis() + 3 * 86400000), // peste 3 zile
					new Date(System.currentTimeMillis() + 4 * 86400000), // peste 4 zile
					new Date(System.currentTimeMillis() + 5 * 86400000), // peste 5 zile
					new Date(System.currentTimeMillis() + 6 * 86400000), // peste 6 zile
					new Date(System.currentTimeMillis() + 7 * 86400000), // peste 7 zile
					new Date(System.currentTimeMillis() + 8 * 86400000), // peste 8 zile
					new Date(System.currentTimeMillis() + 9 * 86400000), // peste 9 zile
					new Date(System.currentTimeMillis() + 10 * 86400000), // peste 10 zile
					new Date(System.currentTimeMillis() + 11 * 86400000), // peste 11 zile
					new Date(System.currentTimeMillis() + 12 * 86400000), // peste 12 zile
					new Date(System.currentTimeMillis() + 13 * 86400000), // peste 13 zile
					new Date(System.currentTimeMillis() + 14 * 86400000), // peste 14 zile
					new Date(System.currentTimeMillis() + 15 * 86400000) // peste 15 zile
			};MyMatchScore[] teamAScores = {
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(5).build(),
					MyMatchScore.builder().firstHalfScore(6).secondHalfScore(4).build(),
					MyMatchScore.builder().firstHalfScore(7).secondHalfScore(3).build(),
					MyMatchScore.builder().firstHalfScore(8).secondHalfScore(2).build(),
					MyMatchScore.builder().firstHalfScore(9).secondHalfScore(1).build(),
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(5).build(),
					MyMatchScore.builder().firstHalfScore(6).secondHalfScore(4).build(),
					MyMatchScore.builder().firstHalfScore(7).secondHalfScore(3).build(),
					MyMatchScore.builder().firstHalfScore(8).secondHalfScore(2).build(),
					MyMatchScore.builder().firstHalfScore(9).secondHalfScore(1).build(),
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(5).build(),
					MyMatchScore.builder().firstHalfScore(6).secondHalfScore(4).build(),
					MyMatchScore.builder().firstHalfScore(7).secondHalfScore(3).build(),
					MyMatchScore.builder().firstHalfScore(8).secondHalfScore(2).build(),
					MyMatchScore.builder().firstHalfScore(9).secondHalfScore(1).build()
			};
			MyMatchScore[] teamBScores = {
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(4).secondHalfScore(6).build(),
					MyMatchScore.builder().firstHalfScore(3).secondHalfScore(7).build(),
					MyMatchScore.builder().firstHalfScore(2).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(1).secondHalfScore(9).build(),
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(4).secondHalfScore(6).build(),
					MyMatchScore.builder().firstHalfScore(3).secondHalfScore(7).build(),
					MyMatchScore.builder().firstHalfScore(2).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(1).secondHalfScore(9).build(),
					MyMatchScore.builder().firstHalfScore(5).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(4).secondHalfScore(6).build(),
					MyMatchScore.builder().firstHalfScore(3).secondHalfScore(7).build(),
					MyMatchScore.builder().firstHalfScore(2).secondHalfScore(8).build(),
					MyMatchScore.builder().firstHalfScore(1).secondHalfScore(9).build()
			};


			MyMatchDTO match = MyMatchDTO.builder()
					.name(matchNames[0])
					.teamA(myUserInMatches.subList(0, 1))
					.teamB(myUserInMatches.subList(2, 3))
					.startTimeMatch(startDates[0])
					.endTimeMatch(endDates[0])
					.teamLeaderA(myUserInMatches.get(0).getMyUser())
					.teamLeaderB(myUserInMatches.get(2).getMyUser())
					.teamAScore(teamAScores[0])
					.teamBScore(teamBScores[0])
					.map(Map.values()[0 % 8].toString())
					.build();
			myMatchService.createMatch(match);
			matches.add(match);

			MyUserTeamCreationDTO myUserTeamCreationDTO = MyUserTeamCreationDTO
					.builder()
					.name("test")
					.matches(List.of("Match1"))
					.teamLeader("s1mple")
					.teamMembers(List.of("s1mple", "niko"))
					.build();



			myUserTeamService.createMyUserTeam(myUserTeamCreationDTO);

		};
	}


}
