syms t1 t2 t3 w1 w2 w3 real;
syms lL lT mL mT mH real;
syms theta1 theta2 theta3 real;
global params;

% define symbolic variables
params.lL = lL;
params.lT = lT;
params.mL = mL;
params.mH = mH;
params.mT = mT;

% shortened sin / cos definitions
s12 = sin(theta1 - theta2);
s13 = sin(theta1 - theta3);
c12 = cos(theta1 - theta2);
c13 = cos(theta1 - theta3);

% matrix D definition
D11 = ((5/4)*params.mL + params.mH + params.mT)*params.lL^2;
D12 = -.5 * params.mL * params.lL^2 * c12;
D13 = params.mT * params.lL * params.lT * c13;
D21 = D12;
D22 = .25 * params.mL * params.lL^2;
D23 = 0;
D31 = D13;
D32 = D23;
D33 = params.mT * params.lT^2;
matD = [D11,D12,D13;D21,D22,D23;D31,D32,D33];

detD = det(matD)

%maximizing cos & sin
s12 = 1;
s13 = 1;
c12 = 1;
c13 = 1;
theta1 = 0
theta2 = 0
theta3 = 0
% matrix D definition
D11 = ((5/4)*params.mL + params.mH + params.mT)*params.lL^2;
D12 = -.5 * params.mL * params.lL^2 * c12;  
D13 = params.mT * params.lL * params.lT * c13;
D21 = D12;
D22 = .25 * params.mL * params.lL^2;
D23 = 0;
D31 = D13;
D32 = D23;
D33 = params.mT * params.lT^2;
matD = [D11,D12,D13;D21,D22,D23;D31,D32,D33];

detDmin = det(matD)