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
oms =simplify(omegas,'Criterion','preferReal','Steps',simpSteps);

[numOms,denOms] = numden(oms);

numOms1 = collect(numOms,wp);
numOms2 = collect(numOms1,sin(tp-ts));
numOms3 = collect(numOms2,wp);

omegaS = numOms3/denOms

pretty(omegaS)