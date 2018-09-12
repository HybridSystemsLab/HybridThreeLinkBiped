syms tp ts tt wp ws wt real;
syms lL lT mL mT mH real;
global params;

% dfeine symbolic variables
params.lL = lL;
params.lT = lT;
params.mL = mL;
params.mH = mH;
params.mT = mT;

% compute speeds after step using symbolic variables
[omegap,omegas,omegat] = computeSpeedsAfterStep(tp,ts,tt,wp,ws,wt) ;

% simplify and print the results
simpSteps = 100;
omegaP =simplify(omegap,'Criterion','preferReal','Steps',simpSteps)

pretty(omegaP)