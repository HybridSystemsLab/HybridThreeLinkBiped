function [ new_omega1, new_omega2, new_omega3 ] = computeSpeedsAfterStep( theta1, theta2, theta3, omega1, omega2, omega3 )
%COMPUTENEWSPEEDS Computes the new values of omega

syms t1plus t2plus t3plus fT fN w1plus w2plus w3plus x_plus y_plus; % define symbolic variables for the 5 unknowns

[matrixD,matrixE,dd] = computeJumpMatrix(theta1,theta2,theta3); % compute the jump matricies D and E
F = [fT; fN]; % define the force vector

% define the auxiliary state before and after step occurs
qe_dot_minus = [omega1;omega2;omega3;0;0];
qe_dot_plus = [w1plus;w2plus;w3plus;x_plus;y_plus];

% define the addditional equations
stateEquation = matrixD*qe_dot_plus - matrixE'*F == matrixD * qe_dot_minus;
forceEquation = matrixE*qe_dot_plus +[0,0;0,0]*F == [0;0];

% define the set of equations to solve
[new_omega2,new_omega1,new_omega3,ft,fn,xplus,yplus] = solve(stateEquation,forceEquation,[w1plus,w2plus,w3plus,fT,fN,x_plus,y_plus]);

% uncomment for printing purposes
% new_omega1 = (new_omega1);
% new_omega2 = (new_omega2);
% new_omega3 = (new_omega3);
% newX = (xplus);
% newY = (yplus);

end