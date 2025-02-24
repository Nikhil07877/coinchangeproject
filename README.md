# Coin Change exercise

Idea of this exercise is to implement a Spring Boot Rest application able to provide change given a input dollar bill.

## Problem

Create a Spring Boot service that exposes a REST endpoint that allows a user to request change for a given bill. The service should assume there is a finite number of coins, and it should update that number on each request

### Requirements
*	Accepted bills are (1, 2, 5, 10, 20, 50, 100), nothing else
*	Available coins are (0.01, 0.05, 0.10, 0.25), nothing else
*	Start with 100 coins of each type
*	Change should be made by utilizing the least amount of coins
*	API should validate bad input and respond accordingly
*	Service should respond with an appropriate message if it does not have enough coins to make change
*	The service should maintain the state of the coins throughout the transactions