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
omt =simplify(omegat,'Criterion','preferReal','Steps',simpSteps);

[numOms,denOms] = numden(omt);

numOms1 = collect(numOms,lL);
numOms2 = collect(numOms1,wp);
numOms3 = collect(numOms2,cos(tp - 2*ts + tt));
numOms4 = collect(numOms3,cos(tp - tt));
numOms5 = collect(numOms4,lT);
numOms6 = collect(numOms5,wt);
omegaT = numOms6/denOms

pretty(omegaT)