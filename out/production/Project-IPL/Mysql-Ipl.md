# IPL Project

## 1. Number of matches played per year of all the years in IPL.
```
select season,
count(season) as Matches_Played
from matches 
group by season 
order by season;

```

## 2. Number of matches won of all teams over all the years of IPL.
```
select winner,
count(winner) as Won
from matches 
group by winner
order by winner;
```
## 3. For the year 2016 get the extra runs conceded per team.
```
select batting_team,
sum(extra_runs) as extra_run from deliveries
JOIN matches
ON matches.id=deliveries.match_id 
where matches.season=2016 
group by batting_team;
```

## 4. For the year 2015 get the top economical bowlers.
```
select bowler,
sum(total_runs)/(count(bowler)/6.0) as Bowler_Economy
from deliveries  join matches  
on matches.season=2015 
where matches.id=deliveries.match_id
group by bowler
order by Bowler_Economy;
```

## 5. Create your own scenario.

```
select if(winner=team1,team2,team1) as loser,
count(if(winner=team1,team2,team1)) as count
from matches 
where season=2015
group by loser;
```

