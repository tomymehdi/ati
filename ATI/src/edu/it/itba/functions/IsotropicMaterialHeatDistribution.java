package edu.it.itba.functions;

public class IsotropicMaterialHeatDistribution extends MaterialHeatDistribution {


	@Override
	public double g(double value ) {
		return 1;
	}
}
